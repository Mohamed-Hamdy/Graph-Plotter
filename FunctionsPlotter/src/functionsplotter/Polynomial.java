package functionsplotter;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    
    private static String equation;
    private static Pattern Coeff = Pattern.compile("[+-]?\\d+(?=\\*|\\/)");
    private static Pattern Degree = Pattern.compile("\\^([+-]?\\d+)");
    private static Pattern operators = Pattern.compile("[*/]");
    private static String Constant = "\\b[+-]?\\d+[*/]x[\\^][+-]?\\d+";
    public static ArrayList<Integer> Degrees;
    public static ArrayList<Integer> Coefficients;
    public static ArrayList<String> Operators;
    public static ArrayList<Integer> Range;
    public static ArrayList<Double> result;
    public static ArrayList<Double> special_function_result;
    
    public static int Const = 0;
    public static boolean Right_Flag = false;
    
    public Polynomial(String poly, int min, int max) {
        equation = CleanString(poly);
        String temp = equation.substring(0, 4);
        String eq_temp = equation;
        //String eq_temp_1 = equation;

        if ("sin(".equals(temp.toLowerCase()) || "cos(".equals(temp.toLowerCase()) || "tan(".equals(temp.toLowerCase())|| "log(".equals(temp.toLowerCase())) {
            // initilize arraylists
            special_function_result = new ArrayList<>();
            Range = new ArrayList<>();
            result = new ArrayList<>();

            // split equation to make if condition in case sin and cos and other functions
            eq_temp = eq_temp.substring(0, eq_temp.indexOf("("));
            equation = equation.substring(equation.indexOf("(") + 1, equation.indexOf(")"));
            
            System.out.println("eq_temp is : " + equation);
            
            for (int i = min; i <= max; i++) {
                Range.add(i);
            }
            equation = CleanString(equation);

            // get Coefficients and Degrees and const and operators from inside part in sin 
            // ex : sin(x+1) it will get the value of x+1
            Coefficients = ExtractCoeff(equation);
            Degrees = ExtractDegree(equation);
            Const = ExtractConstant(equation);
            Operators = ExtractOperators(equation);
            
            System.out.println(Degrees);
            System.out.println(Coefficients);
            System.out.println(Const);
            System.out.println(Operators);
            
            special_function_result = Evaluate(Coefficients, Degrees, Operators, min, max);
            System.out.println("special_function_result : " + special_function_result);

            double b, r;
//            ArrayList<Integer> int_special_function_result = new ArrayList<Integer>();

//            for(Double d : special_function_result){
//                int_special_function_result.add(d.intValue());
//            }
            
            //System.out.println("special_function_result : " + special_function_result);
            //System.out.println("Range : " + int_special_function_result);
            
            if ("sin".equals(eq_temp)) {
                for (Double Value : special_function_result) {
                    r = Math.sin(Value);
                    b = Math.toRadians(r);
                    result.add(b);
                }
            } else if ("cos".equals(eq_temp)) {
                for (Double Value : special_function_result) {
                    r = Math.cos(Value);
                    b = Math.toRadians(r);
                    result.add(b);
                }
            } else if ("tan".equals(eq_temp)) {
                for (Double Value : special_function_result) {
                    r = Math.tan(Value);
                    b = Math.toRadians(r);
                    result.add(b);
                }
            }else if ("log".equals(eq_temp)) {
                for (Double Value : special_function_result) {
                    r = Math.log(Value);
                    //b = Math.toRadians(r);
                    result.add(r);
                }
            }else {
                System.out.println("else");
            }
            Right_Flag = true;
            
        } else {
            
            Range = new ArrayList<>();
            for (int i = min; i <= max; i++) {
                Range.add(i);
            }
            Coefficients = ExtractCoeff(equation);
            Degrees = ExtractDegree(equation);
            Const = ExtractConstant(equation);
            Operators = ExtractOperators(equation);
            
            if (Degrees.size() != Coefficients.size() || Degrees.size() != Operators.size()
                    || Operators.size() != Coefficients.size()) {
                System.out.println("Invalid Polynomial !!");
            } else {
                
                result = Evaluate(Coefficients, Degrees, Operators, min, max);
                Right_Flag = true;
//                System.out.println(Degrees);
//                System.out.println(Coefficients);
//                System.out.println(Const);
//                System.out.println(Operators);
            }
        }
        
    }
    
    public static String CleanString(String in) {
        // 1-Removing all white spaces inside input string
        // 2-Convert all "X" characters into lower case
        // 3-For purpose of Pattern matching all (x) will be converted to x^1
        // 4-If the equation contains (x) without previous coefficient => add(1*) before it
        String out = in.replaceAll("\\s+", "").toLowerCase();
        out = out.replaceAll("x(?!\\^)", "x^1");
        out = out.replaceAll("^(?!\\*d+)x", "1*x");
        out = out.replaceAll("\\+x", "+1*x");
        out = out.replaceAll("\\-x", "-1*x");
        return out;
    }
    
    public static ArrayList<Integer> ExtractCoeff(String eq) {
        ArrayList<Integer> coeffs = new ArrayList<>();
        Matcher CoefMatch = Coeff.matcher(eq);
        //System.out.println("inside 0 ");
        //System.out.println(CoefMatch.find());
        
        try {
            while (CoefMatch.find()) {
                //System.out.println("inside 1 ");
                String Matchedgroup = CoefMatch.group(0);
                Matchedgroup = Matchedgroup.replaceAll("\\+", "");
                coeffs.add(Integer.parseInt(Matchedgroup));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Expression !!");
        } catch (NullPointerException e) {
            System.out.println("No Input Provided !");
        }
        
        return coeffs;
    }
    
    public static ArrayList<Integer> ExtractDegree(String eq) {
        ArrayList<Integer> degrees = new ArrayList<>();
        Matcher DegreeMatch = Degree.matcher(eq);
        try {
            while (DegreeMatch.find()) {
                String MatchedDegree = DegreeMatch.group(1);
                degrees.add(Integer.parseInt(MatchedDegree));
            }
        } catch (NullPointerException e) {
            System.out.println("No Input Provided !");
        } catch (NumberFormatException e) {
            System.out.println("Invalid Expression !");
        }
        return degrees;
    }
    
    public static int ExtractConstant(String eqn) {
        int constant = 0;
        eqn = eqn.replaceAll(Constant, "");
        try {
            constant = Integer.parseInt(eqn);
        } catch (NumberFormatException e) {
            System.out.println("NO CONSTANT TERM ");
        }
        return constant;
    }
    
    public static ArrayList<String> ExtractOperators(String eqn) {
        ArrayList<String> ops = new ArrayList<>();
        Matcher OpsMatch = operators.matcher(eqn);
        try {
            while (OpsMatch.find()) {
                String Matchedops = OpsMatch.group(0);
                ops.add(Matchedops);
            }
        } catch (NullPointerException e) {
            System.out.println("No Input Provided !");
        } catch (NumberFormatException e) {
            System.out.println("Invalid Expression !");
        }
        return ops;
    }
    
    public ArrayList<Double> Evaluate(ArrayList<Integer> cof, ArrayList<Integer> deg,
            ArrayList<String> ops, int min, int max) {
        ArrayList<Double> plot = new ArrayList<>();
        int i = min;
        try {
            while (i <= max) {
                double result = 0;
                for (int j = 0; j < cof.size(); j++) {
                    double holder;
                    if (ops.get(j).equals("*")) {
                        holder = Math.pow(i, deg.get(j)) * cof.get(j);
                        result += holder + Const;
                        plot.add(result);
                    } else if (ops.get(j).equals("/")) {
                        holder = cof.get(j) / (Math.pow(i, deg.get(j)) + Const);
                        result += holder;
                        //System.out.println(result);
                        plot.add(result);
                    }
                }
                if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY) {
                    throw new ArithmeticException();
                }
                
                i++;
            }
            System.out.println(plot.toString());
        } catch (ArithmeticException a) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid Range");
            errorAlert.setContentText("Divide By Zero Exception !");
            errorAlert.showAndWait();
        }
        
        return plot;
    }
    
}
