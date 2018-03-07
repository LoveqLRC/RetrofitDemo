# Retrofit总结

## Get


### @Path
对于`http://192.168.1.18:4567/blog/2` 这种url,其中blog/2中的2是动态变化的,可以使用`@Path`表示

	  public interface BlogService {
	        @GET("blog/{id}")
	            //这里的{id} 表示是一个变量
	        Call<ResponseBody> getBlog(/** 这里的id表示的是上面的{id} */@Path("id") int id);
	    }

### @Query
对于`http://192.168.1.18:4567/blog?id=2` 这种url,其中id=2相当于查询参数，可以使用`@Query`或者`@QueryMap`(针对多个查询参数)表示

	 @GET("/blog")
	 //拼接查询参数/blog?id=1
	 //注意/blog后面不能有/
	 Call<ResponseBody> getBlog(@Query("id") int id);

## Post

### 表单请求

#### @FormUrlEncoded
表示请求体是一个Form表单，一般登录页面用的就是这种请求方式：

`Content-Type: application/x-www-form-urlencoded`

	  public interface BlogService {
	        /**
	         * {@link FormUrlEncoded} 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
	         * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
	         */
	        @POST("/form")
	        @FormUrlEncoded
	        Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);
	
	    }

上面username和age作为key,能看到下面类似的请求

	--> POST http://192.168.1.18:4567/form
	Content-Type: application/x-www-form-urlencoded
	Content-Length: 18
	username=rc&age=18
	--> END POST (18-byte body)

与`@Query`类似，`@Field`有`@FieldMap`支持多参数。


#### @Multipart
表示请求体是一个支持文件上传的Form表单，一般带文件上传的网页用的就是这种请求方式。

`Content-Type: multipart/form-data;`

	 		/**
	         * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
	         * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
	         */
	        @POST("/form")
	        @Multipart
	        Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

与`@Query`类似，`@Part`有`@PartMap`支持多参数。

        /**
         * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
         * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
         * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
         */
        @POST("/form")
        @Multipart
        Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);
