package zad1.LangServer;

import java.util.Map;

public class Dict {
    String code;
    Map<String,String> mapaSlow;

    Dict(String code,Map<String,String> mapa){
        this.code = code;
        this.mapaSlow = mapa;
    }

    public String getTranslation(String slowo){
        slowo = slowo.toLowerCase();
        return mapaSlow.get(slowo);
    }

    public String getCode() {
        return code;
    }
}
