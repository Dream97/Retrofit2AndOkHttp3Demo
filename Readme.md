### 资料
Retrofit介绍：http://square.github.io/retrofit/
OkHttp介绍：http://square.github.io/okhttp/
豆瓣api：https://developers.douban.com/wiki/?title=book_v2#post_book_collection

### 前言
Retrofit是由大名鼎鼎的Square公司开发的Android网络请求框架,它的出身决定它绝对不是一个资质庸俗的框架。目前也是市场上的主流框架之一。Retorfit通过底部封装OkHttp,通过注解配置网络请求参数，并运用各种设计模式来实现代码的简化,还有牛逼哄哄的解耦能力注定了它在Android界的地位。

- 课外阅读: [Retrofit分析-漂亮的解耦套路](https://www.jianshu.com/p/45cb536be2f4)

### 实践
**Demo地址**：https://github.com/Dream97/Retrofit2AndOkHttp3Demo.git
**代码结构**（在[MVP模式Demo](https://github.com/Dream97/MVPDemo.git)的代码上实现的）
![这里写图片描述](https://img-blog.csdn.net/20180608102125659?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0MjYxMjE0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
在看这个代码结构之前，你需要了解一下MVP模式是如何运作的
**效果**
![这里写图片描述](https://img-blog.csdn.net/20180608102716437?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0MjYxMjE0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
#### 步骤1：使用前准备

- 网络权限

```
    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
```

- 添加依赖库

```
    //OkHttp3
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
    //Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
```
默认情况下，Retrofit只能将HTTP主体反序列化为OkHttp的ResponseBody类型，并且只能接受它的RequestBody类型@Body。可以添加转换器来支持其他类型，这里添加converter-gson,用于反序列化JSON字符串成Java对象
#### 步骤2:建立实体类
这里使用了豆瓣的图书api，而它返回的json是这样的(要获取具体一本图书，首先要获取图书的id，图书id可以通过访问图书搜索api获取）文章顶部已经给出豆瓣api的使用链接

```JSON
{
rating: {
max: 10,
numRaters: 108881,
average: "9.0",
min: 0
},
subtitle: "",
author: [
"路遥"
],
pubdate: "2005-1",
tags: [
{
count: 29855,
name: "路遥",
title: "路遥"
},
{
count: 29637,
name: "平凡的世界",
title: "平凡的世界"
},
{
count: 16717,
name: "小说",
title: "小说"
},
{
count: 16078,
name: "中国文学",
title: "中国文学"
},
{
count: 13816,
name: "人生",
title: "人生"
},
{
count: 13304,
name: "经典",
title: "经典"
},
{
count: 8673,
name: "文学",
title: "文学"
},
{
count: 7229,
name: "当代",
title: "当代"
}
],
origin_title: "",
image: "https://img3.doubanio.com/view/subject/m/public/s2335693.jpg",
binding: "平装",
translator: [ ],
catalog: "",
pages: "1631",
images: {
small: "https://img3.doubanio.com/view/subject/s/public/s2335693.jpg",
large: "https://img3.doubanio.com/view/subject/l/public/s2335693.jpg",
medium: "https://img3.doubanio.com/view/subject/m/public/s2335693.jpg"
},
alt: "https://book.douban.com/subject/1200840/",
id: "1200840",
publisher: "人民文学出版社",
isbn10: "702004929X",
isbn13: "9787020049295",
title: "平凡的世界（全三部）",
url: "https://api.douban.com/v2/book/1200840",
alt_title: "",
author_intro: "路遥（1949年—1992年），陕西清涧人。1973年入延安大学中文系学习。1976年大学毕业后，先后在《陕西文艺》和《延河》杂志做编辑工作。1978年开始发表作品。著有长篇小说《平凡的世界》，中篇小说《惊心动魄的一幕》《人生》等。曾任陕西省作家协会副主席。",
summary: "《平凡的世界》是一部现实主义小说，也是一部小说形式的家族史。作者浓缩了中国西北农村的历史变迁过程，在小说中全景式地表现了中国当代城乡的社会生活。在近十年的广阔背景下，通过复杂的矛盾纠葛，刻划社会各阶层众多普通人的形象。劳动与爱情，挫折与追求，痛苦与欢乐，日常生活与巨大社会冲突，纷繁地交织在一起，深刻地展示了普通人在大时代历史进程中所走过的艰难曲折的道路。",
series: {
id: "1571",
title: "茅盾文学奖获奖作品全集"
},
price: "64.00元"
}
```
这里只是测试Demo，不需要全部数据，我简单封装成
**BookBean.java**

```java
public class BookBean {

    private String pubdate;
    private String publisher;
    private String title;

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

```
#### 步骤3：编写Service类
**RetrofitService.java**

```java
public interface RetrofitService {
    /**
     * https://api.douban.com/v2/book/{id}
     * 豆瓣图书信息api
     * get方式
     */
    @GET("v2/book/{id}")
    Call<BookBean> getBook(@Path("id") String id);

}
```
这里是使用Retrofit中要学习的重要一步，这里通过注释设置请求方式，请求参数等等。这里使用get方式，@GET后面的内容是api具体路劲，然而前半截的baseurl后面提到。@Path里面图书的id将url的{id}内容替换，像这样类似的注释还有很多
**各种注解的详细解析:https://blog.csdn.net/qiang_xi/article/details/53959437**
#### 步骤4:编写Helper类
helper类就是Retrofit使用封装OkHttp的具体体现了
**RetorfitHelper.java**

```java
public class RetrofitHelper {
    private static OkHttpClient okHttpClient;
    private RetrofitService retrofitService;

    public RetrofitHelper(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public Call<BookBean> getBook(String id){
        return retrofitService.getBook(id);
    }

    public OkHttpClient getOkHttpClient() {
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}

```
注意看，在helper构造器中我们需要将Retrofit初始化，初始化okhttpClient,并将豆瓣的baseurl传进来。最后通过动态代理实现RetrofitService接口
#### 步骤5:model层调用
**MainModel.java**

```java
public class MainModel {
    public void getData(String id, final MainListener mainListener){

        RetrofitHelper retrofitHelper = new RetrofitHelper(Api.DOUBAN_BASE_HOST);
        retrofitHelper.getBook(id).enqueue(new Callback<BookBean>() {
            @Override
            public void onResponse(Call<BookBean> call, Response<BookBean> response) {
                mainListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BookBean> call, Throwable t) {
                mainListener.onfail();
            }
        });
    }
}

```
在model调用RetrofitHelper中Call,enqueue()方法，开始网络请求业务，然后监听回调,显示等等。后面的步骤在上一篇博客有提到[MVP模式使用总结](https://blog.csdn.net/qq_34261214/article/details/80575171)
### 小结
以上就是使用Retrofit作为网络请求框架的最最最最最基本的使用，要想学好这个框架当然上面这些是远远不够的.这里推荐一些我觉得写得还不错博客，不对，是比我好太多的博客

- [这是一份很详细的 Retrofit 2.0 使用教程（含实例讲解）](https://blog.csdn.net/carson_ho/article/details/73732076) 
- [网络加载框架 - Retrofit](https://www.jianshu.com/p/0fda3132cf98)