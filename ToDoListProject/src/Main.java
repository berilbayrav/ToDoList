import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //JFrame oluşturma
        JFrame jFrame = new JFrame("ToDoList Project");
        JPanel jPanel = new JPanel();
        JPanel todoListPanel = new JPanel();
        todoListPanel.setName("Planlanan İşler");
        JPanel completedPanel = new JPanel();
        completedPanel.setName("Tamamlanan İşler");
        jFrame.setName("ToDoList Project");
        jFrame.setSize(400, 400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
        //Menubar oluşturma
        JMenuBar jMenuBar = new JMenuBar();
        //Hakkımızda menüsü
        JMenu about = new JMenu("Hakkımızda");
        JMenuItem aboutButton = new JMenuItem("Hakkımızda");
        about.add(aboutButton);
        //Ekle menüsü
        JMenu add = new JMenu("Ekle");
        JMenuItem addButton = new JMenuItem("Yeni Ekle");
        add.add(addButton);
        //Çıkış menüsü
        JMenu exit = new JMenu("Çıkış");
        JMenuItem exitButton = new JMenuItem("Çıkış");
        exit.add(exitButton);
        //Menüleri menubarlara ekleme
        jMenuBar.add(about);
        jMenuBar.add(add);
        jMenuBar.add(exit);
        //Paneli jFrame'e ekleme
        jFrame.add(jPanel);
        //Menübarı frame'e ekleme
        jFrame.setJMenuBar(jMenuBar);
        //Yeni tofolist oluşturuyorum
        ToDoList toDoList = new ToDoList();
        //Dosyadan verileri okuyoruz ve listeleri atıyoruz
        File file = new File("db.txt");
        FileReader fileReader = new FileReader(file);
        String line;
        BufferedReader br = new BufferedReader(fileReader);
        while ((line = br.readLine()) != null) {
            //n demek non-complete demek yani todolist de demek
            if (line.split(" ")[1].equals("n")) {
                toDoList.addList(line.split(" ")[0]);
            } else {
                //else c yani completed demek tamamlanmış listeye ekleriz
                toDoList.addCompletedList(line.split(" ")[0]);
            }
        }
        br.close();

        //Jlistleri oluşturuyoruz todolist ve tamamlanan
        JList<String> todoList = new JList<>(toDoList.getTodoList());
        JList<String> completedList = new JList<>(toDoList.getCompletedList());
        todoListPanel.add(new JLabel("Yapılacaklar Listesi"));
        todoListPanel.add(todoList);
        completedPanel.add(new JLabel("Tamamlananlar Listesi"));
        completedPanel.add(completedList);
        jPanel.add(todoListPanel);
        jPanel.add(completedPanel);
        jFrame.add(jPanel);
        //Itemların event'i
        todoList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toDoList.addCompletedList(todoList.getSelectedValue());
                toDoList.delteteList(todoList.getSelectedValue());
            }
        });
        //Hakkımızda action'ı
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame aboutFrame = new JFrame("Hakkımızda");
                aboutFrame.setSize(250, 250);
                aboutFrame.setResizable(false);
                aboutFrame.setVisible(true);
                aboutFrame.setLocationRelativeTo(null);
                //Hakkımızda içeriği buraya yazılır.
                JLabel jLabel = new JLabel();
                jLabel.setText("Hakkımızda içeriği..");
                aboutFrame.add(jLabel);
                aboutFrame.show();
            }
        });
        //Yeni ekleme action'ı
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String todo = JOptionPane.showInputDialog("Yapılacak");
                toDoList.addList(todo);
            }
        });
        //Çıkış action
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Yapılan işler ve yapılacak işler dosyaya kaydedilir
                try {
                    File file = new File("db.txt");
                    FileWriter fileWriter = null;
                    fileWriter = new FileWriter(file, false);
                    BufferedWriter bWriter = new BufferedWriter(fileWriter);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    for (int i = 0; i < toDoList.getTodoList().size(); i++)
                        bWriter.write(toDoList.getTodoList().get(i) + " " + "n\n");
                    for (int i = 0; i < toDoList.getCompletedList().size(); i++)
                        bWriter.write(toDoList.getCompletedList().get(i) + " " + "c\n");
                    bWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });

        jFrame.show();
    }
}
