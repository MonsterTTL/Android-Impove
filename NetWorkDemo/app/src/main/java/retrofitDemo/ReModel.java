package retrofitDemo;

import com.google.gson.annotations.SerializedName;

//数据类-用于解析Json数据
public class ReModel {

    @SerializedName("status")
    public String status;

    @SerializedName("lang")
    public String lang;

    @SerializedName("timezone")
    public String timezone;


}
