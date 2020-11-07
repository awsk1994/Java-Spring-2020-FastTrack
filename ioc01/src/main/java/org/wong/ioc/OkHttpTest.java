package org.wong.ioc;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class OkHttpTest {
    public static void main(String[] args) {
//        // The normal way of creating okHttpClient
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        // Creating okHttpClient via ctx.
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        OkHttpClient okHttpClient = ctx.getBean("okHttpClient", OkHttpClient.class);

        Request request = new Request.Builder()
            .get()
            .url("http://www.baidu.com")
            .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Err = " + e.getMessage());
            }

            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("Body = " + response.body().string());
            }
        });
    }
}
