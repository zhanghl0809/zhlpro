package com.example.Utiles;// package com.example.Utiles;
//
//
// import com.baidu.aip.http.HttpMethodName;
//
// import org.apache.commons.compress.utils.Sets;
//
// import java.net.URISyntaxException;
// import java.util.HashMap;
//
// public class BaiDUJianquan {
//
//
//     public static void main(String[] args) {
//         String ACCESS_KEY_ID = "xxxxxxx";//  用户的Access Key ID
//         String SECRET_ACCESS_KEY = "xxxxxxxx";//  用户的Secret Access Key
//         BceV1Signer v1Signer = new BceV1Signer();
//         URI uri;
//         try {
//             int time=(int) (System.currentTimeMillis() / 1000);
//             System.out.println(time);
//             //传入所要调用的url
//             uri = new URI("URL");
//             InternalRequest internalRequest = new InternalRequest(HttpMethodName.POST , uri);
//             HashMap<String, String> parameters = Maps.newHashMap();
//             //传入的参数
//             parameters.put("app_id", "xxxx");
//             parameters.put("time", ""+time);
//             internalRequest.setParameters(parameters);
//             HashMap<String, String> map = Maps.newHashMap();
//             map.put("Content-Type", "application/json; charset=utf-8");
//             map.put("Host", "cloud.apollo.auto");
//             map.put("x-bce-date", DateUtils.formatAlternateIso8601Date(new Date()));
//             internalRequest.setHeaders(map);
//             SignOptions signOptions = new SignOptions();
//             signOptions.setHeadersToSign(Sets.newHashSet("host","x-bce-date"));
//             v1Signer.sign(internalRequest, new DefaultBceCredentials(ACCESS_KEY_ID, 			      SECRET_ACCESS_KEY),signOptions);
//         } catch (URISyntaxException e) {
//             e.printStackTrace();
//         }
//     }
//
//
// }
