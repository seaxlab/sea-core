package com.github.seaxlab.core.component.pdf;

import lombok.Data;

/**
 * sign config
 *
 * @author spy
 * @version 1.0 2022-06-03
 * @since 1.0
 */
@Data
public class SignConfig {

    private String srcFile;
    private String signedFile;
    private String certPath;
    private String certPwd;
    private Boolean showSignature;
    private String signReason;
    private String signLocation;
}
