package com.github.spy.sea.core.component.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;


/**
 * pdf签名
 *
 * @author shi.pengyan
 * @version 1.0 2017-08-09 9:41
 * @since 1.0
 */
@Slf4j
public class PDFSignUtil {

    /**
     * 对pdf进行签名
     *
     * @param srcFile      源文件
     * @param signedFile   签名文件
     * @param certPath     证书路径
     * @param certPwd      证书密码
     * @param signReason   签名原因
     * @param signLocation 签名地点
     * @throws DocumentException
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void sign(String srcFile, String signedFile, String certPath,
                            String certPwd, String signReason, String signLocation)
            throws DocumentException, IOException, GeneralSecurityException {

        SignConfig config = new SignConfig();

        config.setSrcFile(srcFile);
        config.setSignedFile(signedFile);
        config.setCertPath(certPath);
        config.setCertPwd(certPwd);
        config.setShowSignature(false);
        config.setSignReason(signReason);
        config.setSignLocation(signLocation);

        sign(config);
    }

    /**
     * pdf签名
     *
     * @param signConfig
     * @throws GeneralSecurityException
     * @throws IOException
     * @throws DocumentException
     */
    public static void sign(final SignConfig signConfig)
            throws GeneralSecurityException, IOException, DocumentException {
        // 添加providers (在%JAVA_HOME%/jre/lib/security/java.security中可以找到sun提供的providers )

        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);

        KeyStoreFactory ksFactory = KeyStoreFactory.getInstance();
        PdfReader reader = null;
        FileOutputStream fout = null;
        PdfStamper stp = null;
        PdfSignatureAppearance sap = null;
        ByteArrayOutputStream encryptByteOut = new ByteArrayOutputStream();
        try {
            ksFactory.initKeyStore(signConfig.getCertPath(), signConfig.getCertPwd());

            reader = new PdfReader(signConfig.getSrcFile());
            fout = new FileOutputStream(signConfig.getSignedFile());
            stp = PdfStamper.createSignature(reader, encryptByteOut, '\0');
            sap = stp.getSignatureAppearance();

        } catch (FileNotFoundException e) {
            throw new SignatureException(SignatureException.RCE_INVALID, e.getMessage());
        } catch (DocumentException e) {
            throw new SignatureException(SignatureException.RCE_INVALID, e.getMessage());
        } catch (IOException e) {
            throw new SignatureException(SignatureException.RCE_INVALID, e.getMessage());
        }

        sap.setReason(signConfig.getSignReason());
        sap.setLocation(signConfig.getSignLocation());

        // comment next line to have an invisible signature
        if (signConfig.getShowSignature()) {
            sap.setVisibleSignature(new Rectangle(200, 100, 300, 200), 1, null);
        }

        ExternalDigest digest = new BouncyCastleDigest();
        ExternalSignature signature = new PrivateKeySignature(ksFactory.getPrivateKey(signConfig.getCertPwd()), DigestAlgorithms.SHA256, provider.getName());

        MakeSignature.signDetached(sap, digest, signature, ksFactory.getCertificateChain(), null, null, null, 0, MakeSignature.CryptoStandard.CMS);


        stp.close();
        fout.write(encryptByteOut.toByteArray());
        fout.close();
    }


}
