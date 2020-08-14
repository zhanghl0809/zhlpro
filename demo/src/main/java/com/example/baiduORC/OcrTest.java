package com.example.baiduORC;

import com.baidu.aip.ocr.AipOcr;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.extern.log4j.Log4j;


public class OcrTest {

    /**
     * 通过图片url获取解析的文字
     *
     * @param imgUrl
     * @return
     */
    public synchronized static String getImgContent(String imgUrl) {

        String text = "";
        try {
            //百度AppID
            String APP_ID = "16624338";
            //百度API Key
            String API_KEY = "Br6V0OfvMrz5EnirBEGDI1uc";
            //百度Secret Key
            String SECRET_KEY = "32ZAhTQQXIueyoG8f4ZaHSdLMxGygE2z";
            AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);

            JSONObject resp = client.basicGeneralUrl(imgUrl, new HashMap<String, String>());

            if (resp == null || resp.getJSONArray("words_result") == null) {
                return null;
            }
            JSONArray jsonArray = resp.getJSONArray("words_result");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    if (StringUtils.isBlank(item.getString("words"))) {
                        continue;
                    }
                    String words = item.getString("words");
                    text += words;
                }
            }
            String addressAndNo = text.substring(text.indexOf("址")+1, text.length());
            String[] split = addressAndNo.split("公民身份号码", 4);
            String str = "UPDATE tb_cus_open_acc_msg SET address='"+split[0] +"' WHERE id_no='"+ split[1]+"';";
            System.out.println(str);

            File file = new File("D://账户文件2020//wenjian2.txt");
            RandomAccessFile rf = new RandomAccessFile(file, "rw");
            long fileLength = rf.length();
            rf.seek(fileLength);
            rf.write((str+"\r\n").getBytes());
            rf.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return text;
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/20/9976cfe0203adcc9171242634cfb6fe0.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/18/07d7caa51fe652c2e9590d8dca127689.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/18/df842e1b611e7aacec9ce3677b24f5e5.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/15/d62314b22840b4e451d5b88f2962d6fb.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/14/b2b3d68317d3f62cebd988f85d75343b.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/13/93cc219c3822674a72656d8885d44873.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/13/c1cb06a38d3f881d0ec690148a328230.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/10/c54c6dc60f21070d6277d218a47a52c2.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/10/43559c911bd9ce036f3bd608ba35ee89.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/10/960dcc0a1518929e5a5d355236f17438.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/07/01/07f946c90495d399fb789e3bdf28a697.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/29/d86d4e107efa191ef333aa25d07ebad0.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/28/a3789dd47b38384ab2feecaa68ca597d.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/28/5f70f1ffa78c3eed9f8f9d39727e0129.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/21/15512239c2d9b5e5c325d9c067485d02.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/16/2149784074122078b109b27844768ec1.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/15/49a4f23a5f0aa13738975e41c41b5f22.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/12/efb16040520c2bb7d2a06ff34f9506c6.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/12/f910b7ad6fccad23f41873713a21ecda.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/12/00c07c4fd5c7ad1e154ec00dba9ea53f.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/10/a3cfa3434673267dec3564cb4a787ea5.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/08/28320daed6a61e01d09b33a3865e9951.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/05/d469c1541bac8bdd5b20defe5719237d.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/06/04/db2bf61a14ea9b21d6881f8a9d016558.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/05/23/5a2279b0761512b2bfc3603da9c02444.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/05/19/a3779ad12da113da12d5ff6e474fa623.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/05/18/1b45748d04accd45a330d77efc2a479f.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/05/18/c40a4b2562f9bde5b16ee553788f6eef.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/05/16/c7ceb5c8472c10dab7a45a8e517a6874.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/05/15/3e687d6d40b1d20dc728d2d26351ad26.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/05/14/608407cf560aa48eae35d99034622a79.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/05/13/d500b834d18ce22a4165c91ae5928ed1.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/05/12/d4a0d0cef5e42fa0b58f12f028bd5ae3.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/28/f015da414904c91b8883f4638aa7477c.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/28/d18b26cbfe31e040f097ebd7eb76dc5f.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/26/3134ecfa3362b0e277fcc32e8ccc3c26.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/20/4a65bad2a3aef51c43b911928ff0c567.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/16/9edd52a999049d516e33dbc457f091d2.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/16/c3753c315d81960f64ccd61d7e503b4a.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/16/77eac150f7fac90e8b839c70210cce2b.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/15/30cd26694c2fd5a9c448143252e9378d.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/15/3dc31f96745c562f2a7437e46f2c60be.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/14/91cf339821a29d91f0621103218720ba.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/10/0b093b538add50aea94b8f7e979cf152.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/05/deac80bdc871aef9c8cbe593e5fe6653.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/03/dfca43e05f89ef00c8ae588569847a0b.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/03/06494540561b768b96ba88e9fae4844b.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/01/88fce5946e27ef3eebfd60e769e8d617.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/04/01/32de044bd7afb173d85d89af7ccbed7f.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/30/6e4d7931e29210ae7549177bb057d151.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/27/3d732db88bd4da07dc6ccd5cd80b8108.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/26/466725dc9396c6f8bdae81c81d08ca5d.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/21/1c6de13b4491f3ce6c12dbeabce5608a.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/18/e89b7fd511135287cd78830d4d516869.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/17/d065fdeb62e8a439306aeb80befa5f3a.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/17/5899a58a33784168a5717845eec2014e.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/16/75282cde2e1808819fd8c93b9b5ec1bf.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/13/a5e9b7ed4ca21488bcba46040c396077.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/07/040f6455d76650615743e8ed1d222728.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/03/02/52044db7e69e3fdbeeb1a84e364ed1b5.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/02/28/9f35417781d3e7d7158122968ec5e560.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/02/19/3162edff6496f30eedcc13a0014222f0.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/02/12/da8603cbdc12365edc66edb06eb7541d.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/02/04/faf4855c74a1b436dce4fb0d7e9e3150.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/02/03/5d5a56e3053013eb095ece0d46a32bc8.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/01/19/7ff1cda3fbb507ff441822ac725c4466.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/01/19/022d1b41cbcc199c5978ba95c0414d7e.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/01/19/90eddb2deaef94a8d624165802d9bb2a.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/01/19/a094e0ec722ed7cc8f270b1369b5fe1b.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/01/19/9b89df1bacd25b9e454219d00e3590de.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/01/13/736becea403239b6ef5a1cde784e0ca1.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/01/09/be0341948f83bf4ac974904525c95f6c.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2020/01/02/8c0d9ca36f037975c43cdcd7aac99a5b.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/30/a5b411546a51112a7c56ade47631edc4.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/28/612201340f498682e8d0a50e52703c54.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/27/e18201a17963443486427a5b5622ce90.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/27/331748bd0a4fd290d3b58863f20ee4fc.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/26/6f6c89fbc92ef694a00b841bd8cd5dcb.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/25/dd2db1a70bac4d262618484014c44ec7.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/24/9f2f7aaa04ef9db5d1120a3b630e78d8.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/24/05df3bd582ac82a8b71e0c9ce7efa228.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/24/1cd8b205d78a8d63929e46ce104af058.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/19/94c9cfffd3342e9250271e60dd23a1e8.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/19/0f82faac7bf66a0be25c0675f59272bd.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/18/8e8226a5f39228c2b8cc31a74d62e6cb.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/12/18/7ff7226f0338125c5f621e61ab1ecfc2.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/11/28/3130a782dec04828410ee9ba8037cac2.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/11/06/f2ad6962c7c14f8643b766ba13939a0d.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/11/05/f52cef1a1a1690bbdc8ddd823b197cb7.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/30/d5ab74c3d77e5b7a9f338c3931ab6b48.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/25/43969fa1f1421500eebcef703c25eca7.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/25/7fc2f741da28ce7993e99b5b57493f4d.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/24/8494bfe0c3abe7867a8d9740c73df202.jpg");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/24/bc55d464181f368be92273f3d3ef55f4.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/24/beda25df2d03d0d8f9cd03a7e3e4ddd5.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/24/5ee9687a80fbc97a96ac34d1d8557149.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/24/f18c3641135856fc3783b958dda61082.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/24/ed1ae31e06dd3781d29c3762ff3d70ab.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/23/cba6c51962d344ac393650692389b139.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/23/64917cea275a639c79e7fde2da7712ac.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/23/8ee3e1f5990201c1206d571d6a387bf5.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/23/a8939271c5a9bc5011b22a00f87b9ddc.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/10/18/735945c2e54732bbf3d519fae90dc2af.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/09/10/1b79c7adfbbb2c5449b3007bf6bba737.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/09/09/d06452670472e2604d720a1e80acea2e.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/09/09/1b8cb60a2ab9745adde86e1d75007678.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/09/04/1e64adeac6da0bd55aaa5b92d7631716.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/08/31/33b8e254374093b2ae82719fcda0bc8d.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/08/28/3dec0fa414fab33265ed6c8396ef744f.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/08/20/42d06192bcdee4083d9264d6990b3a1d.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/08/20/8215689355c2e8be9b0adc7b875349f4.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/08/19/c8b1a247c5c878be18988751caee80b2.png");
        // list.add("https://pic.buyforyou.cn/aipc/2019/08/19/a6bcb317149c5a3b16c8ab7f15b623e9.png");


        list.add("https://pic.buyforyou.cn/aipc/2020/07/10/c54c6dc60f21070d6277d218a47a52c2.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2020/03/26/466725dc9396c6f8bdae81c81d08ca5d.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2019/11/06/f2ad6962c7c14f8643b766ba13939a0d.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2020/06/12/f910b7ad6fccad23f41873713a21ecda.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2019/08/19/a6bcb317149c5a3b16c8ab7f15b623e9.png");
        list.add("https://pic.buyforyou.cn/aipc/2020/06/28/a3789dd47b38384ab2feecaa68ca597d.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2019/12/19/0f82faac7bf66a0be25c0675f59272bd.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2020/06/29/d86d4e107efa191ef333aa25d07ebad0.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2020/01/19/7ff1cda3fbb507ff441822ac725c4466.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2019/08/20/42d06192bcdee4083d9264d6990b3a1d.png");
        list.add("https://pic.buyforyou.cn/aipc/2020/05/18/c40a4b2562f9bde5b16ee553788f6eef.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2019/09/04/1e64adeac6da0bd55aaa5b92d7631716.png");
        list.add("https://pic.buyforyou.cn/aipc/2019/10/24/5ee9687a80fbc97a96ac34d1d8557149.png");
        list.add("https://pic.buyforyou.cn/aipc/2019/10/23/64917cea275a639c79e7fde2da7712ac.png");
        list.add("https://pic.buyforyou.cn/aipc/2020/04/15/30cd26694c2fd5a9c448143252e9378d.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2019/10/24/8494bfe0c3abe7867a8d9740c73df202.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2019/10/25/43969fa1f1421500eebcef703c25eca7.jpg");
        list.add("https://pic.buyforyou.cn/aipc/2020/06/15/49a4f23a5f0aa13738975e41c41b5f22.jpg");

        for (String url : list) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getImgContent(url);
        }



    }

}

