package com.zoe.snow.enums;

/**
 * 操作系统平台枚举
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/10
 */
public enum  EPlatformEnum {
    ANY("any"),
    LINUX("Linux"),
    MAC_OS("Mac OS"),
    MAC_OS_X("Mac OS X"),
    WINDOWS("Windows"),
    OS2("OS/2"),
    SOLARIS("Solaris"),
    SUNOS("SunOS"),
    MPEiX("MPE/iX"),
    HP_UX("HP-UX"),
    AIX("AIX"),
    OS390("OS/390"),
    FREEBSD("FreeBSD"),
    IRIX("Irix"),
    DIGITAL_UNIX("Digital Unix"),
    NET_WARE_411("NetWare"),
    OSF1("OSF1"),
    OPENVMS("OpenVMS"),
    OTHERS("Others");

    private String description;

    private EPlatformEnum(String desc){
        this.description = desc;
    }

    public String toString(){
        return description;
    }
}
