插件功能： 验证数字签名【工具类】

支持的签名算法：MD5,RSA,DSA,ECDSA

目前工程拥有两种使用方法，分别为算法类静态调用、工厂多态调用，任选其一便可（推荐使用第一种），下面分别介绍。

一、算法类静态调用
    
    1.在工程pom文件中添加如下代码：
    
    <groupId>com.weibo.datasys</groupId>
    <artifactId>dsa-utils</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    2.选择签名算法类
    
    类名分别为：MD5、RSA、DSA、ECDSA
    
    3.调用接口：
    
    /**
     * 生成数字签名
     * @param messages 源消息
     * @param key      密钥
     * @return 签名
     */
    public boolean verify(String h, List<String> messages, String publicKey);//验证签名
    
    /**
     * 生成数字签名
     * @param messages 源消息
     * @param key      密钥
     * @return 签名
     */
    public static String generateSignature(List<String> messages, String key);//生成签名
    
二、工厂多态调用

    1.在工程pom文件中添加如下代码：
   
    <groupId>com.weibo.datasys</groupId>
    <artifactId>dsa-utils-factory</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    2.使用DSAFactory的getInstance方法获取相应的签名算法
    
    DSAFactory.getInstance(DSASet.MD5);//其中DSASet为算法枚举集合，取值为MD5,RSA,DSA,ECDSA，对应相应的算法
    
    算法通用接口如下：
   
        /**
         * 生成数字签名
         * @param messages 源消息
         * @param key      密钥
         * @return 签名
         */
        public boolean verify(String h, List<String> messages, String publicKey);//验证签名
        
        /**
         * 生成数字签名
         * @param messages 源消息
         * @param key      密钥
         * @return 签名
         */
        public static String generateSignature(List<String> messages, String key);//生成签名
      
            
    使用示例如下：
    
        //verify
        boolean valid = DSAFactory.getInstance(DSASet.ECDSA).verify(sign, new ArrayList<String>(){
                {
                    add(message);
                }
            }, publicKey, true);
            

说明：

    工具中RSA,DSA,ECDSA算法使用前提需要有对应算法的公钥、私钥，可以使用其他工具生成，或者使用本插件自带，生成示例如下：
    
    List<String> pairList = DSA.generatePrivateAndPublicKey();
    List<String> pairList = RSA.generatePrivateAndPublicKey();
    List<String> pairList = ECDSA.generatePrivateAndPublicKey();
    
    返回列表中包含两个值，其中0位是私钥，1位是公钥。公钥、私钥全部采用BASE64编码。
    
   