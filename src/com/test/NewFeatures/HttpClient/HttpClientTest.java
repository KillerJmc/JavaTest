package com.test.NewFeatures.HttpClient;

import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

public class HttpClientTest {
	public static void main(String[] args) throws Exception {
		test01();
		test02();
	}

	//同步
	private static void test01() throws Exception {
		var url = "http://www.baidu.com";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
		BodyHandler<String> responseBodyHandler = BodyHandlers.ofString();
		HttpResponse<String> response = client.send(request, responseBodyHandler);
		String body = response.body();
		System.out.println(body);
	}
	
	//异步
	private static void test02() throws Exception {
		var url = "http://www.baidu.com";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
		BodyHandler<String> responseBodyHandler = BodyHandlers.ofString();
		CompletableFuture<HttpResponse<String>> sendAsync = client.sendAsync(request, responseBodyHandler);
		HttpResponse<String> response = sendAsync.get();
		String body = response.body();
		System.out.println(body);
	}
}
