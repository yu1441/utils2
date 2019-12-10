package com.topkl.libapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yujing.utils.YConvert;
import com.yujing.utils.YObjectStorage;
import com.yujing.utils.YPicture;
import com.yujing.utils.YToast;
import com.yujing.utils.YVersionUpdate;
import com.yujing.ycrash.YCrash;
import com.yujing.ycrash.YJson;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    static {
        YObjectStorage.setUseCache(true);
    }

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.imageView)
    ImageView imageView;

    YPicture yPicture = new YPicture();
    YVersionUpdate versionUpdate = new YVersionUpdate();

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "测试测试", Snackbar.LENGTH_LONG).setAction("余静", null).show();
            }
        });

        // 检测权限，如果没有权限就弹窗系统对话框开启权限（在华为mate8 安卓6.0下，需要动态检测权限，安卓6.0新机制）
        String[] Permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE};
        for (String ps : Permissions) {
            if (ContextCompat.checkSelfPermission(this, ps) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, Permissions, 1);// 申请权限
            }
        }
        yPicture.setPictureFromAlbumListener(new YPicture.PictureFromAlbumListener() {
            @Override
            public void result(Uri uri, File file, Object Flag) {
                Log.i("Album", file.getAbsolutePath());
                imageView.setImageURI(uri);
                yPicture.gotoCrop(MainActivity.this, uri, 300, 400);
            }
        });
        yPicture.setPictureFromCameraListener(new YPicture.PictureFromCameraListener() {
            @Override
            public void result(Uri uri, File file, Object Flag) {
                Log.i("Camera", file.getAbsolutePath());
                imageView.setImageURI(uri);
                yPicture.gotoCrop(MainActivity.this, uri, 400, 300);
            }
        });
        yPicture.setPictureFromCropListener(new YPicture.PictureFromCropListener() {
            @Override
            public void result(Uri uri, File file, Object Flag) {
                Log.i("Crop", file.getAbsolutePath());
                imageView.setImageURI(uri);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (versionUpdate != null)
            versionUpdate.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (versionUpdate != null)
            versionUpdate.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_exit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1: {
//                String url = "http://www.topkl.com";
//                Request request = new Request.Builder()
//                        .url(url)
//                        .build();
//               client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        System.out.println("错误："+e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        if (!response.isSuccessful())
//                            throw new IOException("Unexpected code " + response);
//                        Headers responseHeaders = response.headers();
//                        for (int i = 0; i < responseHeaders.size(); i++) {
//                            System.out.println("请求信息："+responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                        }
//                        System.out.println(response.body().string());
//                    }
//                });


//                String url = "http://39.106.6.5:8080/user/login";
//                FormBody formBody = new FormBody.Builder().add("name", "yujing").add("password", "yujing").build();
//                Request request = new Request.Builder().url(url).post(formBody).build();
//
//                System.out.println("请求信息request：" + request.toString());
//                StringBuilder formBodyString = new StringBuilder();
//                for (int i = 0; i < formBody.size(); i++)
//                    formBodyString.append(i == 0 ? "" : ",").append(formBody.encodedName(i)).append("=").append(formBody.encodedValue(i));
//                System.out.println("请求参数：" + formBodyString.toString());
//
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        System.out.println("错误：" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        if (!response.isSuccessful()) {
//                            System.out.println("错误：" + response);
//                            return;
//                        }
//                        Headers responseHeaders = response.headers();
//                        for (int i = 0; i < responseHeaders.size(); i++)
//                            System.out.println("响应信息response：" + responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                        String value=response.body().string();
//                        System.out.println(value);
//                    }
//                });
                break;
            }
            case R.id.button2: {
//                final String url = "http://118.120.16.116:8888/cmdsys/views/mobileManager/mcmdsys2.1.2.35.20180402_BETA_223750.apk";
//                versionUpdate.setActivity(this);
//                versionUpdate.setDownUrl(url);
//                versionUpdate.setForceUpdate(false);
//                versionUpdate.setServerCode(100);
//                versionUpdate.setUseNotificationDownload(false);
//                versionUpdate.checkUpdate();
                try {
                    https();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.button3:
                try {
                    https();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button4: {
                yPicture.gotoCamera(this);
                break;
            }
            case R.id.button5: {

                break;
            }
            case R.id.button6: {
                startActivity(new Intent(this, MainActivity2.class));
                break;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        yPicture.onActivityResult(requestCode, resultCode, data);
    }


    public void jsonTest() {

        List<Object> list0 = new ArrayList<>();
        list0.add("外网机111");
        list0.add("外网机222");
        list0.add("外网机333");

        TestUser t0 = new TestUser();
        t0.名字 = "余";
        t0.其他 = "静";
        t0.list = list0;

        Map<String, Object> m = new HashMap<>();
        m.put("aa", "1111");
        m.put("bb", 222.2);
        m.put("cc", false);
        m.put("dd", t0);

        TestUser t = new TestUser();
        t.名字 = "哈哈";
        t.其他 = "123123";
        t.map = m;

        Map<String, Object> a = new HashMap<>();
        a.put("aa", "阿可接受的");
        a.put("bb", true);
        a.put("cc", 12345);
        a.put("tt", t);

        List<Object> list = new ArrayList<>();
        list.add(t);
        list.add(t);

        try {
            String json = YJson.toJson(list);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void https() throws Exception {
        String crt = "-----BEGIN CERTIFICATE-----\n" +
                "MIIFPDCCBCSgAwIBAgIBATANBgkqhkiG9w0BAQsFADCBpDELMAkGA1UEBhMCQ04x\n" +
                "EDAOBgNVBAgTB3NpY2h1YW4xEDAOBgNVBAcTB2NoZW5nZHUxGTAXBgNVBAoTEHl1\n" +
                "amluZ2dvbmd6dW9zaGkxEDAOBgNVBAsTB2thaWZhYnUxFTATBgNVBAMTDDMyMDAw\n" +
                "LjMyMC5pbzEPMA0GA1UEKRMGeXVqaW5nMRwwGgYJKoZIhvcNAQkBFg15dTE0NDFA\n" +
                "cXEuY29tMB4XDTE5MDcwNDAzMjY1NFoXDTI5MDcwMTAzMjY1NFowgaQxCzAJBgNV\n" +
                "BAYTAkNOMRAwDgYDVQQIEwdzaWNodWFuMRAwDgYDVQQHEwdjaGVuZ2R1MRkwFwYD\n" +
                "VQQKExB5dWppbmdnb25nenVvc2hpMRAwDgYDVQQLEwdrYWlmYWJ1MRUwEwYDVQQD\n" +
                "EwwzMjAwMC4zMjAuaW8xDzANBgNVBCkTBnl1amluZzEcMBoGCSqGSIb3DQEJARYN\n" +
                "eXUxNDQxQHFxLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALmy\n" +
                "LHD9GHoks2SRC6Ee2hLuWariPjPLYy7V8wVS2BC8b6IYp44u5Npqwu80pd5l79hN\n" +
                "x+MrCRDKf/YaWasjIb+RjYhiltYlAtvXYN81fWbEe9KITMNULV79nJ5J2nhCNqgL\n" +
                "3hO/x/5gMKkhj0lssD/DFlZRWB7R12MAOavUs6ipK2MGvViCLrcpcerW0kBe+/f9\n" +
                "7dactWJjVHa/e1hLL4TZg07WAf3soiBjAs1DDXgMVYLyhlfgXiqJu3lZWxylRvp2\n" +
                "z69QNPGDTDo3/qfHuMgsHSFO8ohQ7X7Veynxk2MGVWkwio2+Ywq2FT2krB4bhdXT\n" +
                "9zAKl04WyRgtfb1tX6MCAwEAAaOCAXUwggFxMAkGA1UdEwQCMAAwEQYJYIZIAYb4\n" +
                "QgEBBAQDAgZAMDQGCWCGSAGG+EIBDQQnFiVFYXN5LVJTQSBHZW5lcmF0ZWQgU2Vy\n" +
                "dmVyIENlcnRpZmljYXRlMB0GA1UdDgQWBBTAN4ohNUiZdCsaxc5kPaEXgwjh2jCB\n" +
                "2QYDVR0jBIHRMIHOgBTx/bque5MEcW7jkc+Sa+oT0FTdsqGBqqSBpzCBpDELMAkG\n" +
                "A1UEBhMCQ04xEDAOBgNVBAgTB3NpY2h1YW4xEDAOBgNVBAcTB2NoZW5nZHUxGTAX\n" +
                "BgNVBAoTEHl1amluZ2dvbmd6dW9zaGkxEDAOBgNVBAsTB2thaWZhYnUxFTATBgNV\n" +
                "BAMTDDMyMDAwLjMyMC5pbzEPMA0GA1UEKRMGeXVqaW5nMRwwGgYJKoZIhvcNAQkB\n" +
                "Fg15dTE0NDFAcXEuY29tggkA5OdOOv2/2vcwEwYDVR0lBAwwCgYIKwYBBQUHAwEw\n" +
                "CwYDVR0PBAQDAgWgMA0GCSqGSIb3DQEBCwUAA4IBAQCMOm87KtOuRkHA7EJnFXRt\n" +
                "qqZn3raOFH4xTnd6bOcPs0nf2PF+YcQnqIACOUZMyQVAUUTMtaZ+0KK+yMhtS4xt\n" +
                "IUbB/rdg0kQV+bKrXs+p59vG5ji9Ej5McmsDZ/xQpAQznYuQaaiY9LF+i4r7k89W\n" +
                "oA9gP0b8FdJzpVKHqTkQmqWFXqWH5qNocUoaN1IEIxA8+I+JkS348zBx9AkCG13o\n" +
                "4rBI+PGmEB4T4kcULVOBpcZOgTDD+SRFkol+RBYZn5tzeTOuFDPSbbd+5eijNj3T\n" +
                "fKBAAb70RAWg5NOSRGxatrV6HD9DmwlisWDO4B0lnXccz/eaZXrgUa6bbx7kegJ3\n" +
                "-----END CERTIFICATE-----";

        YCrash.AppInfo appInfo = new YCrash.AppInfo();
        appInfo.imei = "111";
        appInfo.其他信息 = "222";
        appInfo.崩溃信息 = "333";
        appInfo.isDebug = "true";
        appInfo.软件名称 = " 测试";
        appInfo.包名 = "com";
        appInfo.版本号 = "1";
        appInfo.版本名 = "1.0";

        Map<String, Object> paramsMap1 = new HashMap<>();
        paramsMap1.put("appInfo", YJson.toJson(appInfo));
        String url1 = "https://192.168.1.78:8080/crash/submit";

    }

    private void post(final String url, final Map<String, Object> paramsMap) {
        post(url, paramsMap, null);
    }

    private void post(final String url, final Map<String, Object> paramsMap, final String crtString) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder params = new StringBuilder();
                if (paramsMap != null) {
                    for (Map.Entry<String, Object> element : paramsMap.entrySet()) {
                        if (element.getValue() == null)
                            continue;
                        params.append(element.getKey()).append("=").append(element.getValue()).append("&");
                    }
                    if (params.length() > 0)
                        params.deleteCharAt(params.length() - 1);
                }
                // 打开和URL之间的连接
                try {
                    HttpURLConnection httpURLConnection = create(url, crtString);
                    httpURLConnection.setConnectTimeout(1000 * 3);
                    httpURLConnection.setReadTimeout(1000 * 3);// 读取数据超时连续X秒没有读取到数据直接判断超时，不影响正在传输的数据（如：下载）
                    // 设置通用的请求属性
                    httpURLConnection.setUseCaches(false); // 设置缓存
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Charset", "utf-8");
                    httpURLConnection.setRequestMethod("POST");
                    // 发送POST请求必须设置如下两行
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream output = httpURLConnection.getOutputStream();
                    // 字符处理
                    OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
                    // 获取URLConnection对象对应的输出流
                    PrintWriter out = new PrintWriter(writer);
                    // 发送请求参数
                    out.write(params.toString());
                    // flush输出流的缓冲
                    out.flush();
                    out.close();
                    // 根据ResponseCode判断连接是否成功
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode != HttpURLConnection.HTTP_OK) {
                        Log.e("Main", "异常提交，失败！code：" + responseCode);
                        return;
                    }
                    // 定义BufferedReader输入流来读取URL的ResponseData
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String response = YConvert.inputStream2String(inputStream);
                    Log.i("Main", response);
                } catch (java.net.SocketTimeoutException e) {
                    Log.e("Main", "异常提交网络超时");
                } catch (Exception e) {
                    Log.e("Main", "异常提时交发生异常：", e);
                }
            }
        });
        thread.start();
    }

    public HttpURLConnection create(String url, String crtSSL) throws Exception {
        if (crtSSL != null && (url.toLowerCase().contains("https://"))) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) (new URL(url)).openConnection();
            httpsURLConnection.setSSLSocketFactory(createSSLSocketFactory(crtSSL));
            //屏蔽https验证
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return httpsURLConnection;
        } else {
            return (HttpURLConnection) (new URL(url)).openConnection();
        }
    }

    public SSLSocketFactory createSSLSocketFactory(String crtString) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca = cf.generateCertificate(new ByteArrayInputStream(crtString.getBytes()));

        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        return context.getSocketFactory();
    }

}
