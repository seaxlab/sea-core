package com.github.spy.sea.core.support.notify.manager.impl;

import com.github.spy.sea.core.exception.UnsupportedOperationException;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.support.notify.dto.MailNotifyDTO;
import com.github.spy.sea.core.support.notify.dto.MailServerConfigDTO;
import com.github.spy.sea.core.support.notify.manager.NotifyManager;
import com.github.spy.sea.core.util.StringUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.SimpleEmail;

import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */
@Slf4j
public class MailNotifyManager implements NotifyManager<MailNotifyDTO> {
    @Override
    public void send(String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(String title, String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BaseResult send(MailNotifyDTO dto) {
        BaseResult result = BaseResult.fail();

        MailServerConfigDTO mailConfig = dto.getMailServerConfigDTO();

        Preconditions.checkNotNull(mailConfig, "mail server config cannot be null.");

        try {
            log.info("send email begin.");

            SimpleEmail mail = new SimpleEmail();
            // 设置邮箱服务器信息
            mail.setSmtpPort(mailConfig.getPort());
            if (mailConfig.getIsSsl()) {
                mail.setSslSmtpPort(mailConfig.getSslPort().toString());
            }
            mail.setHostName(mailConfig.getHost());
            // TODO 设置密码验证器(授权码）
            mail.setAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
            // 设置邮件发送者
//            mail.setFrom(mailConfig.getDefaultSender());
            mail.setFrom(mailConfig.getDefaultSender(), StringUtil.defaultIfEmpty(mailConfig.getDefaultSenderName(), mailConfig.getDefaultSender()));
            // 设置邮件接收者
            mail.addTo(dto.getTo());

            if (StringUtil.isNotEmpty(dto.getCc())) {
                mail.addCc(StringUtil.split(dto.getCc(), ';'));
            }
            if (StringUtil.isNotEmpty(dto.getBcc())) {
                mail.addBcc(StringUtil.split(dto.getBcc(), ';'));
            }

            // 设置邮件编码
            mail.setCharset("UTF-8");
            // 设置邮件主题
            mail.setSubject(dto.getTitle());
            // 设置邮件内容
            mail.setMsg(dto.getContent());
            // 设置邮件发送时间
            mail.setSentDate(new Date());

            mail.setDebug(mailConfig.getDebug());

            // 发送邮件
            mail.send();

            result.setSuccess(true);
        } catch (Exception e) {
            log.error("fail to send mail", e);
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
        } finally {
            log.info("send mail end. ret={}", result);
        }

        return result;
    }
}
