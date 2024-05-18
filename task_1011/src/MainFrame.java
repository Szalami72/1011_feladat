import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainFrame extends JFrame {

    String[] goods = {
            "uborka", "paprika", "retek", "hagyma",
            "paradicsom", "karalábé", "sárgarépa", "fehérrépa",
            "zeller", "fokhagyma", "karfiol", "káposzta",
            "vaj", "túró", "tejszín", "olívaolaj"
    };
    Integer[] prices = {
            400, 800, 300, 450,
            900, 280, 455, 1500,
            1200, 2500, 780, 760,
            899, 680, 650, 3000
    };
    JPanel panel = new JPanel();
    JTextField sum = new JTextField(20);
    JButton buttonSave = new JButton("Rögzít");
    JButton buttonIdea = new JButton("Ötlet");

    ArrayList<Goods> goodsList = new ArrayList<>();
    ArrayList<Integer> selected = new ArrayList<>();

    JCheckBox[] checkBoxes;
    int sumPrices = 0;

    public MainFrame() {

        goodsList = createList();
        checkBoxes = new JCheckBox[goodsList.size()];

        initFrame();

    }

    private void initFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        this.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(buttonSave);
        buttonPanel.add(buttonIdea);

        this.add(buttonPanel, BorderLayout.SOUTH);

        JPanel sumPanel = new JPanel();
        sumPanel.setLayout(new FlowLayout());
        sum.setText(String.valueOf(sumPrices));
        sumPanel.add(sum);
        this.add(sumPanel, BorderLayout.NORTH);

        JPanel chkBoxPanel = new JPanel();
        chkBoxPanel.setLayout(new FlowLayout());

        chkBoxPanel.setLayout(new GridLayout(0, 4));
        for (Goods g : goodsList) {
            JCheckBox chkBox = new JCheckBox(g.getName());
            chkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    changeItem(g.getPrice(), chkBox.isSelected());
                }
            });
            chkBoxPanel.add(chkBox);
            checkBoxes[goodsList.indexOf(g)] = chkBox;
        }

        this.add(chkBoxPanel, BorderLayout.CENTER);
        buttonIdea.addActionListener(e -> createIdea());

    }

    private void changeItem(int price, boolean selected) {
        if (selected) {
            sumPrices += price;
        } else {
            sumPrices -= price;
        }
        sum.setText(String.valueOf(sumPrices));

        if (sumPrices > 3000) {
            buttonSave.setEnabled(false);
        } else {
            buttonSave.setEnabled(true);
        }
    }

    private ArrayList<Goods> createList() {
        for (int i = 0; i < goods.length; i++) {
            goodsList.add(new Goods(goods[i], prices[i]));
        }
        return goodsList;
    }

    private void createIdea() {
        int selectedGoods = 0;
        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isSelected()) {
                selectedGoods++;
                selected.add(i);
            }
        }
        while (selectedGoods <= 3) {
            Random r = new Random();
            int index = r.nextInt(goodsList.size());
            if (!selected.contains(index)) {
                selected.add(index);
                checkBoxes[index].setSelected(true);
                selectedGoods++;
            }
        }

    }

}
