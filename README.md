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
    
    
    说明：
    
    工具中的RSA,DSA,ECDSA算法使用前提需要有公钥、私钥，可以使用其他工具生成，或者使用插件自带，生成示例如下：
    
    List<String> pairList = DSA.generatePrivateAndPublicKey();
    List<String> pairList = RSA.generatePrivateAndPublicKey();
    List<String> pairList = ECDSA.generatePrivateAndPublicKey();
    
    返回列表中包含两个值，其中0位是私钥，1位是公钥。
    公钥全部采用BASE64编码。
    
    
    
    