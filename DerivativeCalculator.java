import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.jar.JarEntry;

public class DerivativeCalculator
{
    public static void main(String[] args)
    {

        JFrame frame = new JFrame("Derivative Calculator");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel note = new JLabel("in a(x)^b form, a and b can be positive or negative and whole or decimals. They cannot be fractions.");
        JLabel note1 = new JLabel("neither a nor b have to be there. Please put your variable in parenthesis like this: (x). ex: 3(x)^2");
        JLabel note2 = new JLabel("and seperate all functions with prantheses. ex: sin(cos(x))");
        JLabel note3 = new JLabel("Special functions are sin, cos, tan, and ln");
        JLabel space = new JLabel(" ");
        frame.add(note);
        frame.add(note1);
        frame.add(note2);
        frame.add(note3);

        JPanel panel = new JPanel();
        LTPanel expression = new LTPanel("expression", 15);
        panel.add(expression);
        JButton differentiate = new JButton("differentiate");
        panel.add(differentiate);
        LTPanel answer = new LTPanel("answer", 35);
        panel.add(answer);
        JLabel label = new JLabel("please do not use product rule or quotient rule expressions in the expression field");
        panel.add(label, BorderLayout.SOUTH);
        frame.add(panel);


        JPanel product = new JPanel();
        LTPanel productPanel = new LTPanel("product rule function", 15);
        product.add(productPanel);
        JButton productRule = new JButton("differentiate");
        product.add(productRule);
        LTPanel productAnswer = new LTPanel("answer", 35);
        product.add(productAnswer);
        JLabel composite = new JLabel("place functions in parentheses and have your variable in parentheses. ex. ((x)^2) * (cos(x)) ");
        product.add(composite, BorderLayout.SOUTH);
        frame.add(product);



        JPanel quotient = new JPanel();
        LTPanel quotientPanel = new LTPanel("quotient rule function", 15);
        quotient.add(quotientPanel);
        JButton quotientRule = new JButton("differentiate");
        quotient.add(quotientRule);
        LTPanel quotientAnswer = new LTPanel("answer", 35);
        quotient.add(quotientAnswer);
        JLabel ex = new JLabel("same syntax as product rule. ex. ((x)^2) / (cos(x)) ");
        quotient.add(ex, BorderLayout.SOUTH);
        frame.add(quotient);



        frame.add(Box.createVerticalGlue());

        differentiate.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                String str = expression.getText();
                answer.setText(derivative(str));


            }
        });

        productRule.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                String str = productPanel.getText();
                productAnswer.setText(productRule(str));


            }
        });

        quotientRule.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                String str = quotientPanel.getText();
                quotientAnswer.setText(quotientRule(str));


            }
        });


        panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        note.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        note1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        note2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        note3.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        composite.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        productPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        ex.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        Box.Filler glue = (Box.Filler) Box.createVerticalGlue();

        frame.pack();
        frame.setSize(850, 500);
        frame.setVisible(true);
    }


    public static String quotientRule(String str)
    {
        String[] arr = str.split("/");
        String exp1 = arr[0].substring(1, arr[0].lastIndexOf(")"));
        String exp2 = arr[1].substring(2, arr[1].lastIndexOf(")"));
        String top1 = "(" + (exp2 + ") * (" + derivative(exp1)) + ")";
        String top2 = "(" + (exp1 + ") * (" + derivative(exp2)) + ")";
        return ("(" + top1 + " - " + top2 + ") / (" + exp2 + ")^2");
    }

    public static String productRule(String str)
    {
        String[] arr = str.split("\\*");
        String exp1 = arr[0].substring(1, arr[0].lastIndexOf(")"));
        String exp2 = arr[1].substring(2, arr[1].lastIndexOf(")"));

        return ("("+ derivative(exp1) + ")"+ " * " + "("+ exp2 + ")" + " + " + "("+ derivative(exp2) + ")" + " * " + "("+ exp1) + ")";
    }

    public static String derivative(String str)
    {
        String retStr = "";
        String[] arr = str.split(" \\+ ");
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i<arr.length; i++)
        {
            String a = chainRule(arr[i]);
            if(!(a.equals("0")))
            {
                list.add(a);
            }
        }
        for(int i = 0; i<list.size(); i++)
        {
            if(i==list.size()-1)
            {
                retStr += (list.get(i));
            }
            else
            {
                retStr += (list.get(i) + " + ");
            }

        }
        return retStr;
    }

    public static String differentiate(String str)
    {
        int a =  str.indexOf("cos");
        int b =  str.indexOf("sin");
        int c =  str.indexOf("tan");
        int d =  str.indexOf("ln");
        int e = str.indexOf("^");
        if(a==-1)
        {
            a=100;
        }
        if(b==-1)
        {
            b=100;
        }
        if(c==-1)
        {
            c=100;
        }
        if(d==-1)
        {
            d=100;
        }

        if(e==-1)
        {
            e = 100;
        }




        if(str.indexOf("e") != -1 && (e== Math.min(a, Math.min(b, Math.min(c, Math.min(d,e)))) && str.lastIndexOf(")")==str.length()-1))
        {
            return str;
        }
        else if(a != 100 && (a== Math.min(a, Math.min(b, Math.min(c, Math.min(d,e)))) && str.lastIndexOf(")")==str.length()-1))
        {
            String var = str.substring(str.indexOf("("), str.lastIndexOf(")")+1);
            if(str.indexOf("cos")!=0)
            {
                String coeffecient = str.substring(0, str.indexOf("cos"));
                return("-" + coeffecient + "sin" + var);
            }
            else
            {
                return("-sin" + var);
            }
        }
        else if(b!=100 && (b== Math.min(a, Math.min(b, Math.min(c, Math.min(d,e)))) && str.indexOf(")")==str.length()-1))
        {
            String var = str.substring(str.indexOf("("), str.lastIndexOf(")")+1);
            if(str.indexOf("sin")!=0)
            {
                String coeffecient = str.substring(0, str.indexOf("sin"));
                return(coeffecient + "cos" + var);
            }
            else
            {
                return("cos" + var);
            }
        }
        else if(c!=100 && (c== Math.min(a, Math.min(b, Math.min(c, Math.min(d,e)))) && str.indexOf(")")==str.length()-1))
        {
            String var = str.substring(str.indexOf("("), str.lastIndexOf(")")+1);
            if(str.indexOf("tan")!=0)
            {
                String coeffecient = str.substring(0, str.indexOf("tan"));
                return(coeffecient + "sec^2" + var);
            }
            else
            {
                return("sec^2" + var);
            }
        }
        else if(d!=100 && (d== Math.min(a, Math.min(b, Math.min(c, Math.min(d,e)))) && str.indexOf(")")==str.length()-1))
        {
            String var = str.substring(str.indexOf("("), str.lastIndexOf(")")+1);
            if(str.indexOf("ln")!=0)
            {
                String coeffecient = str.substring(0, str.indexOf("ln"));
                return(coeffecient + "/" + var);
            }
            else
            {
                return("1/" + var);
            }
        }
        else if(e!=100)
        {

            if(str.indexOf("(") != 0)
            {
                double exp = Double.parseDouble(str.substring(str.lastIndexOf("^")+1))-1;
                double coef = (exp+1) * Double.parseDouble(str.substring(0, str.indexOf("(")));
                String var = str.substring(str.indexOf("("), str.lastIndexOf(")")+1);
                if(exp==1.0)
                {
                    return (coef + var + "");
                }
                else
                {
                    return(coef + var + "^" + exp);
                }

            }
            else
            {
                double exp = Double.parseDouble(str.substring(str.lastIndexOf("^")+1))-1;
                double coef = exp+1;
                String var = str.substring(str.indexOf("("), str.lastIndexOf(")")+1);
                if(exp==1.0)
                {
                    return (coef + var + "");
                }
                else
                {
                    return(coef + var + "^" + exp);
                }
            }
        }
        else if(str.indexOf("(") != -1)
        {
            if(str.indexOf("(") != 0)
            {
                double coef = Double.parseDouble(str.substring(0, str.indexOf("(")));
                return coef + "";
            }
            else
            {
                return("1.0");
            }
        }
        else
        {
            return("0");
        }
    }

    public static String chainRule(String str)
    {

        if(count(str, '(') ==1)
        {
            return(differentiate(str));
        }
        else
        {
            String retStr = "";
            ArrayList<String> list = new ArrayList<>();
            list.add(str);
            while (count(str, '(') > 1) {
                str = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"));
                list.add(str);
            }
            for (int i = list.size() - 1; i >= 0; i--) {
                if(i!=0)
                {
                    retStr += (differentiate(list.get(i)) + "*");
                }
                else
                {
                    retStr += (differentiate(list.get(i)));
                }
            }
            int sign = count(retStr, '-');
            retStr = retStr.replace("-", "");
            if (sign % 2 == 1) {
                retStr = "-" + retStr;
            }
            return(retStr);
        }
    }

    public static int count(String str, char ch)
    {
        int count = 0;
        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == ch)
            count++;
        }
        return count;
    }
}
