package org.quaink.calculator.integer; // 包名，这个可以自己改

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class IntegerCalculator extends JFrame implements MouseListener {
    static IntegerCalculator integerCalculator = new IntegerCalculator(); // 实例化
    static JTextField textInput = new JTextField(""); // 算式文本框
    static JTextField textResult = new JTextField("0"); // 结果文本框
    static JLabel label = new JLabel("="); // 标签
    static JPanel panel = new JPanel(); // 按钮组面板
    static JButton[][] buttons = new JButton[5][4]; // 按钮二维数组
    static String[][] buttonsName = { // 按钮上显示的文本
            {"AC", "(", ")", "/"},
            {"7", "8", "9", "*"},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "+"},
            {".", "0", "+/-", "="}
    };

    char[] stringChars = new char[100]; // 字符串的字符数组
    float[] numberStack = new float[50]; // 操作数栈
    char[] operationStack = new char[50]; // 操作符栈
    int stringCharsIndex = -1; // 字符数组元素序号
    int numberStackIndex = -1; // 操作数栈元素序号
    int operationStackIndex = 0; // 操作符栈元素序号

    public static void main(String[] args) {
        // 窗体
        integerCalculator.setTitle("整数多项式计算器 Designed by QuainK"); // 窗体标题
        integerCalculator.setSize(600, 550); // 窗体大小
        integerCalculator.setResizable(false); // 禁用窗体可调大小
        integerCalculator.setLocationRelativeTo(null); // 窗体相对位置参考为空
        // 控件容器
        Container container = integerCalculator.getContentPane(); // 容器
        container.setLayout(null); // 无布局设置
        // 文本框
        textInput.setBounds(50, 10, 500, 50); // 文本框位置和大小
        textInput.setFont(new Font("黑体", Font.BOLD, 20)); // 文本框字体
        textInput.setHorizontalAlignment(JTextField.RIGHT); // 文字右对齐
        textResult.setBounds(50, 80, 500, 50);
        textResult.setFont(new Font("黑体", Font.BOLD, 20));
        textResult.setHorizontalAlignment(JTextField.RIGHT);
        // 标签
        label.setBounds(30, 80, 50, 50); // 标签位置和大小
        label.setFont(new Font("黑体", Font.BOLD, 20)); // 标签字体
        // 按钮组
        GridLayout grid = new GridLayout(5, 4); // 按钮组网格布局
        panel.setLayout(grid);
        panel.setBounds(150, 150, 300, 350); // 按钮组位置和大小
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 3; j++) {
                buttons[i][j] = new JButton(buttonsName[i][j]);
                buttons[i][j].setFont(new Font("黑体", Font.BOLD, 20));
                buttons[i][j].setSize(50, 50);
                buttons[i][j].setMargin(new Insets(2, 2, 2, 2)); // 边距
                panel.add(buttons[i][j]); // 按钮添加到面板
                buttons[i][j].addMouseListener(integerCalculator); // 按钮添加鼠标事件监听器
            }
        }
        // 控件添加到容器
        container.add(textInput);
        container.add(label);
        container.add(textResult);
        container.add(panel);
        integerCalculator.setVisible(true);
    }

    /*
     * 到此为止，上面的代码书上都有，稍加修改即可使用，下面的是自己写的代码。
     */

    /* 鼠标事件处理方法（5个都不能少） */
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        /*
         * 判断是哪个按钮被按下。
         * 但我想的这种方法有时按钮按下没反应，要按两次，
         * 估计是循环判断时按钮已经弹起，循环判断还没结束，
         * 优化以后再说（虽然不大可能，滑稽.jpg）
         */
        int line, column = 0;
        ok:
        for (line = 0; line <= 4; line++) {
            for (column = 0; column <= 3; column++) {
                if (e.getSource() == buttons[line][column])
                    break ok;
            }
        }
        // 执行对应按钮的功能
        switch (line) {
            case 0:
                switch (column) {
                    case 0:
                        resetAll();
                        textInput.setText("");
                        textResult.setText("");
                        break;
                    case 1:
                        inputString("(");
                        break;
                    case 2:
                        inputString(")");
                        break;
                    case 3:
                        inputString("/");
                        break;
                    // 弹出一个错误提示框，固定格式
                    default:
                        JOptionPane.showMessageDialog(integerCalculator.getContentPane(),
                                "按钮扫描出错 - 第1行 (line = 0)", "错误",
                                JOptionPane.WARNING_MESSAGE);
                        break;
                }
                break;
            case 1:
                switch (column) {
                    case 0:
                        inputString("7");
                        break;
                    case 1:
                        inputString("8");
                        break;
                    case 2:
                        inputString("9");
                        break;
                    case 3:
                        inputString("*");
                        break;
                    default:
                        JOptionPane.showMessageDialog(integerCalculator.getContentPane(),
                                "按钮扫描出错 - 第2行 (line = 1)", "错误",
                                JOptionPane.WARNING_MESSAGE);
                        break;
                }
                break;
            case 2:
                switch (column) {
                    case 0:
                        inputString("4");
                        break;
                    case 1:
                        inputString("5");
                        break;
                    case 2:
                        inputString("6");
                        break;
                    case 3:
                        inputString("-");
                        break;
                    default:
                        JOptionPane.showMessageDialog(integerCalculator.getContentPane(),
                                "按钮扫描出错 - 第3行 (line = 2)", "错误",
                                JOptionPane.WARNING_MESSAGE);
                        break;
                }
                break;
            case 3:
                switch (column) {
                    case 0:
                        inputString("1");
                        break;
                    case 1:
                        inputString("2");
                        break;
                    case 2:
                        inputString("3");
                        break;
                    case 3:
                        inputString("+");
                        break;
                    default:
                        JOptionPane.showMessageDialog(integerCalculator.getContentPane(),
                                "按钮扫描出错 - 第4行 (line = 3)", "错误",
                                JOptionPane.WARNING_MESSAGE);
                        break;
                }
                break;
            case 4:
                switch (column) {
                    case 0:
                        inputString(".");
                        break;
                    case 1:
                        inputString("0");
                        break;
                    case 2:
                        inputString("-");
                        break;
                    case 3:
                        scanString();
                        break;
                    default:
                        JOptionPane.showMessageDialog(integerCalculator.getContentPane(),
                                "按钮扫描出错 - 第5行 (line = 4)", "错误",
                                JOptionPane.WARNING_MESSAGE);
                        break;
                }
                break;
            default:
                JOptionPane.showMessageDialog(integerCalculator.getContentPane(),
                        "按钮扫描出错", "错误", JOptionPane.WARNING_MESSAGE);
                break;
        }
    }

    /* 向算式文本框输入字符串 */
    public void inputString(String s) {
        /*
         * 字符串叠加。
         * 比如输入 "1+2*3"，
         * 就是从 1，1+，1+2，1+2* 到 1+2*3
         */
        textInput.setText(textInput.getText() + s);
    }

    /* 全部清除，归零，计算器的AC */
    public void resetAll() {
        // 重新创建数组和栈
        stringChars = null;
        numberStack = null;
        operationStack = null;
        stringChars = new char[100];
        numberStack = new float[50];
        operationStack = new char[50];
        // 数组和栈的索引回到初始位置
        stringCharsIndex = -1;
        numberStackIndex = -1;
        operationStackIndex = 0;
    }

    /* 操作数入栈 */
    public void pushNumber(float number) {
        numberStack[++numberStackIndex] = number;
    }

    /* 操作数出栈 */
    public void popNumber() {
        numberStackIndex--;
    }

    /* 操作符入栈 */
    public void pushOperation(char operation) {
        operationStack[++operationStackIndex] = operation;
    }

    /* 操作符出栈 */
    public void popOperation() {
        operationStackIndex--;
    }

    /* 读操作数栈顶元素 */
    public float readTopNumber() {
        return numberStack[numberStackIndex];
    }

    /* 运算栈顶两个数 */
    public float operateNumber(float num1, char op, float num2) {
        float result = 0;
        switch (op) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                // 非零除数
                if (num2 != 0) {
                    result = num1 / num2;
                }
                break;
        }
        return result;
    }

    /* 处理操作符 */
    public void handlerOperation() {
        int flag;
        float tmp = 0;
        // 比较优先级
        flag = compareOperation(operationStack[operationStackIndex], stringChars[stringCharsIndex]);
        if (flag == 1) { // 前面的操作符优先级高，先计算，再判断优先级，最后入栈
            // 考虑到语法错误，如果出错就提醒用户，并全部重置
            try {
                tmp = operateNumber(numberStack[numberStackIndex - 1],
                        operationStack[operationStackIndex],
                        numberStack[numberStackIndex]);
            } catch (ArrayIndexOutOfBoundsException e) {
                textResult.setText("运算表达式时出错，可能存在语法错误");
                resetAll();
            }
            popNumber();
            popNumber();
            pushNumber(tmp);
            popOperation();
            printData();
            handlerOperation();
        } else if (flag == 2) { // 不运算直接入栈
            pushOperation(stringChars[stringCharsIndex]);
        } else if (flag == 3) { // 脱括号
            popOperation();
            stringCharsIndex++;
            printData();
            handlerOperation();
        }
    }

    /* 扫描字符串 */
    public void scanString() {
        int numFlag = 0, strLength;
        float numTmp;
        operationStack[0] = '#';
        // 获取数组有效长度
        try {
            for (stringCharsIndex = 0;
                 textInput.getText().charAt(stringCharsIndex) != 0;
                 stringCharsIndex++) {
                // 字符串转字符数组
                stringChars[stringCharsIndex] = textInput.getText().charAt(stringCharsIndex);
            }
        } catch (Exception e) {
            System.out.println("Final stringCharsIndex = " + stringCharsIndex);
        }
        stringChars[stringCharsIndex] = '#';
        strLength = stringCharsIndex + 1;
        stringCharsIndex = 0;
        for (stringCharsIndex = 0; stringCharsIndex < strLength; stringCharsIndex++) {
            // 读取到数字
            if (stringChars[stringCharsIndex] >= '0' && stringChars[stringCharsIndex] <= '9') {
                if (numFlag == 0) { // 数字的第一位
                    numFlag = 1; // 打开读取多位数字状态
                    pushNumber(stringChars[stringCharsIndex] - '0'); // 入栈
                } else { // 如果数字有多位
                    numTmp = stringChars[stringCharsIndex] - '0';
                    // 把栈顶提取出来往前移一位
                    // 比如栈顶是 6，当前数字第二位是 5，则从 6 变成 6*10+5=65，往后以此类推
                    numTmp = numTmp + readTopNumber() * 10;
                    popNumber(); // 出栈
                    pushNumber(numTmp); // 入栈
                }
            } else { // 读取到操作符
                numFlag = 0; // 关闭读取多位数字的状态
                handlerOperation(); // 处理操作符
            }
            printData();
        }
        if (numberStackIndex == 0) {
            textResult.setText(String.valueOf(numberStack[0])); // 显示结果
        } else {
            textResult.setText("运算表达式时出错，可能存在语法错误");
        }
        resetAll(); // 重置全部数据，准备新的计算
    }

    /* 操作符优先级判断，返回值 1 计算，2 不计算，3 脱括号，-1 表达式语法有错 */
    public int compareOperation(char op1, char op2) {
        switch (op2) {
            case '+': {
                switch (op1) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        return 1;
                    case '(':
                    case '#':
                        return 2;
                    case ')':
                    default:
                        return -1;
                }
            }
            case '-': {
                switch (op1) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        return 1;
                    case '(': {
                        // 负数的解决方法，比如 -6，转换成 0-6，方便计算
                        if (stringChars[stringCharsIndex - 1] == '(') {
                            pushNumber(0);
                        }
                        return 2;
                    }
                    case '#':
                        return 2;
                    case ')':
                    default:
                        return -1;
                }
            }
            case '*':
            case '/': {
                switch (op1) {
                    case '+':
                    case '-':
                    case '(':
                    case '#':
                        return 2;
                    case '*':
                    case '/':
                        return 1;
                    case ')':
                    default:
                        return -1;
                }
            }
            case '(': {
                switch (op1) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                    case '(':
                    case '#':
                        return 2;
                    case ')':
                    default:
                        return -1;
                }
            }
            case ')': {
                switch (op1) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        return 1;
                    case '(':
                        return 3;
                    case ')':
                    case '#':
                    default:
                        return -1;
                }
            }
            case '#': {
                if (operationStackIndex == 0) {
                    return 2;
                }
                switch (op1) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        return 1;
                    case '#':
                        return 3;
                    case '(':
                    case ')':
                    default:
                        return -1;
                }
            }
            default:
                return -1;
        }
    }

    /* 在控制台打印数据 */
    public void printData() {
        int i;
        System.out.print("\n");
        for (i = 0; i <= operationStackIndex; i++) {
            System.out.printf("op[%d]%s\t", i, operationStack[i]);
        }
        for (i = 0; i <= numberStackIndex; i++) {
            System.out.printf("num[%d]%f\t", i, numberStack[i]);
        }
        System.out.print("\n");
    }
}
