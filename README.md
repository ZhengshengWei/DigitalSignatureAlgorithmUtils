插件功能： 验证数字签名

支持的签名算法：MD5,RSA,DSA,ECDSA

使用方法：
    
    1.在工程pom文件中加入如下代码：
    <groupId>com.weibo.datasys</groupId>
    <artifactId>dsa-utils</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    2.使用DSAFactory的getInstance方法获取相应的签名算法，示例如下：        
    //verify
    boolean valid = DSAFactory.getInstance(DSASet.ECDSA).verify(sign, new ArrayList<String>(){
            {
                add(message);
            }
        }, publicKey, true);
        
    此外，还提供生成签名的接口，如下
    /**
     * 生成数字签名
     * @param message 源消息
     * @param  key 密钥
     * @return 签名
     */
    public String generateSignature(String message, String key);