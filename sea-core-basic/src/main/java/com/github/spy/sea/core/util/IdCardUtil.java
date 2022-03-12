package com.github.spy.sea.core.util;

import cn.hutool.core.util.IdcardUtil;
import com.github.spy.sea.core.enums.GenderEnum;
import com.github.spy.sea.core.model.IdCard;
import com.github.spy.sea.core.model.NativePlace;
import com.github.spy.sea.core.model.Result;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 身份证号解析
 *
 * @author spy
 * @version 1.0 2020/11/12
 * @since 1.0
 */
@Slf4j
public class IdCardUtil {

    public static boolean isValid(String idNo) {
        return IdcardUtil.isValidCard(idNo);
    }

    /**
     * 身份证号解析
     *
     * @param idNo 身份证号 ，15/18
     * @return
     */
    public static Result<IdCard> parse(String idNo) {
        Result<IdCard> result = Result.fail();

        try {
            boolean isValid = isValid(idNo);
            if (!isValid) {
                result.setMsg("身份证不合法。");
                return result;
            }

            IdCard idCard = new IdCard();

            idCard.setMainlandFlag(idNo.length() == OLD_CARD_NUMBER_LENGTH || idNo.length() == NEW_CARD_NUMBER_LENGTH);
            if (idCard.isMainlandFlag()) {
                // 统一转成18位
                idNo = checkLength(idNo);
                idCard.setNativePlace(getNativePlace(idNo));

                // 生日部分
                Date birthday = getBirthdayDate(idNo);
                idCard.setBirthday(birthday);
                idCard.setBirthdayStr(getBirthdayStr(idNo));
                idCard.setAge(Long.valueOf(BirthdayUtil.getAge(birthday)).intValue());
                idCard.setMonthsOfAge(BirthdayUtil.getMonths(birthday));
                idCard.setDaysOfAge(BirthdayUtil.getDays(birthday));

                GenderEnum genderEnum = getGender(idNo);
                idCard.setSex(genderEnum.getCode());
                idCard.setSexLabel(genderEnum.getDesc());
            }

            idCard.setHkFlag(IdcardUtil.isValidHKCard(idNo));
            idCard.setTwFlag(IdcardUtil.isValidTWCard(idNo));

            result.value(idCard);
        } catch (Exception e) {
            log.error("fail to parse id card", e);
            result.setMsg("身份证解析异常");
        }
        return result;
    }


    private final static int NEW_CARD_NUMBER_LENGTH = 18;
    private final static int OLD_CARD_NUMBER_LENGTH = 15;
    // 身份证的最小出生日期,1900年1月1日
    private final static Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L);

    /**
     * 18位身份证中最后一位校验码
     */
    private final static char[] VERIFY_CODE = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    /**
     * 18位身份证中，各个数字的生成校验码时的权值
     */
    private final static int[] VERIFY_CODE_WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 如果是15位身份证号码，则自动转换为18位
     *
     * @param cardNumber
     */
    private static String checkLength(String cardNumber) {
        if (null != cardNumber) {
            cardNumber = cardNumber.trim();
            if (OLD_CARD_NUMBER_LENGTH == cardNumber.length()) {
                cardNumber = convertToNewCardNumber(cardNumber);
            }
        }
        return cardNumber;
    }

    /**
     * 把15位身份证号码转换到18位身份证号码<br>
     * 15位身份证号码与18位身份证号码的区别为：<br>
     * 1、15位身份证号码中，"出生年份"字段是2位，转换时需要补入"19"，表示20世纪<br>
     * 2、15位身份证无最后一位校验码。18位身份证中，校验码根据根据前17位生成
     *
     * @param oldCardNumber
     * @return
     */
    private static String convertToNewCardNumber(String oldCardNumber) {
        StringBuilder buf = new StringBuilder(NEW_CARD_NUMBER_LENGTH);
        buf.append(oldCardNumber.substring(0, 6));
        buf.append("19");
        buf.append(oldCardNumber.substring(6));
        buf.append(calculateVerifyCode(buf));
        return buf.toString();
    }

    /**
     * <li>校验码（第十八位数）：<br/>
     * <ul>
     * <li>十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0...16 ，先对前17位数字的权求和；
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
     * 2；</li>
     * <li>计算模 Y = mod(S, 11)</li>
     * <li>通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2</li>
     * </ul>
     *
     * @param cardNumber
     * @return
     */
    private static char calculateVerifyCode(CharSequence cardNumber) {
        int sum = 0;
        for (int i = 0; i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            sum += ((int) (ch - '0')) * VERIFY_CODE_WEIGHT[i];
        }
        return VERIFY_CODE[sum % 11];
    }

    /**
     * 验证身份证号码合理性
     *
     * @return
     */
    private static boolean validate(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }
        boolean result = true;
        // 身份证号不能为空
        result = result && (null != cardNumber);
        // 身份证号长度是18(新证)
        result = result && NEW_CARD_NUMBER_LENGTH == cardNumber.length();
        // 身份证号的前17位必须是阿拉伯数字
        for (int i = 0; result && i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            result = result && ch >= '0' && ch <= '9';
        }
        // 身份证号的第18位校验正确
        result = result
                && (calculateVerifyCode(cardNumber) == cardNumber
                .charAt(NEW_CARD_NUMBER_LENGTH - 1));
        // 出生日期不能晚于当前时间，并且不能早于1900年
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date birthDate = sdf.parse(cardNumber.substring(6, 14));
            result = result && null != birthDate;
            result = result && birthDate.before(new Date());
            result = result && birthDate.after(MINIMAL_BIRTH_DATE);
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * 查询详细籍贯信息
     *
     * @param cardNumber
     * @return
     */
    private static NativePlace getNativePlace(String cardNumber) {
        String addressCode = cardNumber.substring(0, 6);
        return AreaCodeUtil.getNativePlace(Integer.valueOf(addressCode));
    }

    public static String getBirthdayStr(String idNo) {
        if (idNo == null || idNo.isEmpty()) {
            return "";
        }

        if (idNo.length() == OLD_CARD_NUMBER_LENGTH || idNo.length() == NEW_CARD_NUMBER_LENGTH) {
        } else {
            log.warn("card number length is not {} or {}, so return null", OLD_CARD_NUMBER_LENGTH, NEW_CARD_NUMBER_LENGTH);
            return "";
        }
        try {
            String birthday = idNo.substring(6, 14);
            StringBuilder sb = new StringBuilder(birthday);
            sb.insert(6, "-");
            sb.insert(4, "-");
            return sb.toString();
        } catch (Exception e) {
            log.error("fail to parse IdNo={}", idNo, e);
        }
        return "";
    }

    /**
     * 获取出生年月日
     *
     * @param idNo
     * @return
     */
    public static Date getBirthdayDate(String idNo) {
        if (idNo == null || idNo.isEmpty()) {
            return null;
        }

        if (idNo.length() == OLD_CARD_NUMBER_LENGTH || idNo.length() == NEW_CARD_NUMBER_LENGTH) {
        } else {
            log.warn("card number length is not {} or {}, so return null", OLD_CARD_NUMBER_LENGTH, NEW_CARD_NUMBER_LENGTH);
            return null;
        }

        try {
            String year = idNo.substring(6, 10);
            String month = idNo.substring(10, 12);
            String day = idNo.substring(12, 14);

            return DateUtil.strDate(year + month + day, DateUtil.DAY_FORMAT2);
        } catch (Exception e) {
            log.error("fail to parse idNo={}", idNo, e);
        }

        return null;
    }

    /**
     * 获取性别
     *
     * @param cardNumber
     * @return
     */
    private static GenderEnum getGender(String cardNumber) {
        char genderCode = cardNumber.charAt(NEW_CARD_NUMBER_LENGTH - 2);
        int code = Integer.valueOf(genderCode) % 2;
        return code == 0 ? GenderEnum.WOMAN : GenderEnum.MAN;
    }


}
