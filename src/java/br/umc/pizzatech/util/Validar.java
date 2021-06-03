package br.umc.pizzatech.util;

import br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validar {

    public static boolean isEmail(String email) {
        if(email == null)
            return false;
        Matcher matcher = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE).matcher(email);
        return matcher.matches();
    }

    public static boolean isLong(String number) {
        try {
            Long.parseLong(number);
            return true;
        } catch (Exception exception){
            return false;
        }
    }

    public static boolean isDouble(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (Exception exception){
            return false;
        }
    }

    public static boolean isInt(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception exception){
            return false;
        }
    }

    public static double isValor(String number) {
        try {
            double v = Double.parseDouble(number);
            if(v > 0)
                return v;
            return -1;
        } catch (Exception exception){
            return -1;
        }
    }

    public static int isEstoque(String number) {
        try {
            int v = Integer.parseInt(number);
            if(v > 0)
                return v;
            return -1;
        } catch (Exception exception){
            return -1;
        }
    }

    public static LevelAcesso isLevelAcesso(String number) {
        if(isInt(number)){
            return LevelAcesso.getById(Integer.parseInt(number));
        }
        return null;
    }

}
