package Enums;

public enum language {

    ENGLISH("EN","English"),
    DUTCH("NL","Nederlands"),
    SPANISH("ES","Español"),
    GERMAN("DE","Deutsch"),
    FRENCH("FR","Français"),
    ITALIAN("IT","Italiano"),
    POLISH("PL","Polski"),
    PORTUGUESE("PT","Português"),
    ROMANIAN("RO","Română"),
    SIMPLIFIED_CHINESE("ZH-HANS","简体中文"),
    TRADITIONAL_CHINESE("ZH-HANT","繁體中文"),
    SWEDISH("SV","Svenska"),
    DANISH("DA","Dansk"),
    GREEK("EL","Ελληνικά");

    private final String displayCode;
    private final String displayLanguage;

    language(String displayCode, String displayLanguage){
        this.displayCode = displayCode;
        this.displayLanguage = displayLanguage;
    }

    public String getDisplayCode(){
        return displayCode;
    }

    public String getDisplayLanguage(){
        return displayLanguage;
    }

    
}
