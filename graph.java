import java.util.ArrayList;
import java.util.HashMap;

public class graph {

    public static void createGraph(String ag) {

        String str = ag;
        ArrayList<String> list = new ArrayList<>();
        list.add(str);
        HashMap<Integer, ArrayList<String>> hashMap = new HashMap<>();
        hashMap.put(0, list);

        for (int i = 1; i< str.length()+1; i++){
            ArrayList<String> newList = new ArrayList<>();
            hashMap.put(i, newList);
        }

        generateGraph(hashMap, 0);

        for (int i = 0; i < hashMap.size(); i++){
            System.out.print(i+": ");
            for (int j = 0; j < hashMap.get(i).size(); j++){
                System.out.print(hashMap.get(i).get(j)+" ");
            }
            System.out.println();
        }

    }

    static void generateGraph(HashMap<Integer, ArrayList<String>> hashMap, int height){

        ArrayList<String> top = new ArrayList<>();

        if (hashMap.get(height).get(0).length()==1) return;

        for (int i = 0; i < hashMap.get(height).size(); i++){
            ArrayList<String> list = generateStringList(hashMap.get(height).get(i));
            top.addAll(list);
        }
        hashMap.put(height+1, top);
        generateGraph(hashMap, height+1);
    }

    static ArrayList<String> generateStringList(String str){

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            String newStr = "";
            switch (i){
                case 0: {
                    if (str.charAt(0)=='X'){
                        newStr += "O";
                    }else{
                        newStr += "X";
                    }
                    for (int j = 1; j < str.length(); j++){
                        newStr+=str.charAt(j);
                    }
                    break;
                }
                case 1:{
                    newStr = newStr+str.charAt(0);
                    if (str.charAt(1)=='X'){
                        newStr += "O";
                    }else{
                        newStr += "X";
                    }
                    for (int j = 2; j < str.length(); j++){
                        newStr+=str.charAt(j);
                    }
                    break;
                }
                case 2:{
                    if (str.charAt(0)=='X'){
                        newStr += 'O';
                    }else newStr += 'X';

                    if (str.charAt(1)=='X'){
                        newStr += 'O';
                    }else newStr += 'X';
                    for (int j = 2; j < str.length(); j++){
                        newStr+=str.charAt(j);
                    }
                    break;
                }
            }
            newStr = checkString(newStr);
            list.add(newStr);
        }

        return list;
    }

    static String checkString(String str){
        HashMap<String, String> map = new HashMap<>();
        map.put("XX", "O");
        map.put("OO", "X");
        map.put("XO", "O");
        map.put("OX", "X");

        String small = str.substring(0,2);
        String newSmall = map.get(small);

        for (int i = 2; i < str.length(); i++){
            newSmall+=str.charAt(i);
        }
        return newSmall;
    }

}