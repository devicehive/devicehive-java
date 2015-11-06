package io.swagger.client.model;

import io.swagger.client.StringUtil;

import com.google.gson.annotations.SerializedName;



import io.swagger.annotations.*;



@ApiModel(description = "")
public class JsonStringWrapper   {
  
  @SerializedName("jsonString")
  private String jsonString = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getJsonString() {
    return jsonString;
  }
  public void setJsonString(String jsonString) {
    this.jsonString = jsonString;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonStringWrapper {\n");
    
    sb.append("    jsonString: ").append(StringUtil.toIndentedString(jsonString)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
