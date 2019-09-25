package cn.inslee.adminback.common.util;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;

/**
 * 算数验证码文本
 *
 * @author dean.lee
 * <p>
 */
public class MathTextCreator extends DefaultTextCreator {

    @Override
    public String getText(){
        StringBuilder codeStr = new StringBuilder();
        Random random = new Random();
        int a = random.nextInt(10);
        int b = random.nextInt(10);
        int type = random.nextInt(3);
        Integer result = null;
        switch (type){
            case 0:
                result = a + b;
                codeStr.append(a).append("+").append(b).append("=?").append(result);
                break;
            case 1:
                if(a >= b) {
                    result = a - b;
                    codeStr.append(a).append("-").append(b).append("=?").append(result);
                }
                else{
                    result = b - a;
                    codeStr.append(b).append("-").append(a).append("=?").append(result);
                }
                break;
            default:
                result = a * b;
                codeStr.append(a).append("*").append(b).append("=?").append(result);
                break;
        }

        return codeStr.toString();
    }
}
