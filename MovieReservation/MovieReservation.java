package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieReservation extends JFrame {
    private JPanel rightPanel; //영화 좌석 선택을 위한 주 패널
    private CardLayout cardLayout; //rightPanel 내의 3가지 패널 관리

    //메인 클래스의 생성자
    public MovieReservation() {
        setTitle("영화 예매 시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //하단 패널 생성
        JPanel bottomPanel = new JPanel();
        JLabel reservationLabel = new JLabel("좌석을 선택해주세요.");
        bottomPanel.add(reservationLabel);

        //오른쪽 메인 패널 생성
        rightPanel = new JPanel();
        cardLayout = new CardLayout();
        rightPanel.setLayout(cardLayout);
        //영화 종류에 따른 3가지 패널 생성 
        JPanel rightPanel1 = create_RightPanel("영화1", reservationLabel);
        JPanel rightPanel2 = create_RightPanel("영화2", reservationLabel);
        JPanel rightPanel3 = create_RightPanel("영화3", reservationLabel);
        //오른쪽 메인 패널에 3가지 패널 추가
        rightPanel.add(rightPanel1, "영화1");
        rightPanel.add(rightPanel2, "영화2");
        rightPanel.add(rightPanel3, "영화3");

        //왼쪽 패널 생성
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 0, 0));
        //3가지 버튼 생성
        JButton LeftButton1 = create_Button("영화1 (10:00 AM)", "영화1");
        JButton LeftButton2 = create_Button("영화2 (12:00 PM)", "영화2");
        JButton LeftButton3 = create_Button("영화3 (3:00 PM)", "영화3");
        //왼쪽 패널에 생성한 버튼 추가
        leftPanel.add(LeftButton1);
        leftPanel.add(LeftButton2);
        leftPanel.add(LeftButton3);

        // 메인 프레임에 추가
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        setSize(850, 500);
        setVisible(true);
    }

    //leftPanel의 버튼 생성 메소드
    public JButton create_Button(String text, String panelName) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
            	//버튼이 눌릴 때마다 해당 패널이 상단으로 올라옴
                cardLayout.show(rightPanel, panelName);
            }
        });
        return button;
    }

    //rightPanel의 영화 종류에 따른 패널 생성 메소드
    public JPanel create_RightPanel(String movieName, JLabel reservationLabel) {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.gray);
        rightPanel.setLayout(new BorderLayout());
        JLabel rightText = new JLabel(" " + movieName + " 좌석 선택", SwingConstants.LEFT);

        //좌석 선택 버튼이 모인 seatPanel 생성
        JPanel seatPanel = new JPanel();
        seatPanel.setBackground(Color.GRAY);
        seatPanel.setLayout(new GridLayout(5, 10, 10, 10));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for(int i = 1; i < 6; i++)
            for(int j = 1; j < 11; j++) {
                String seatNumber = i+"-"+j;
                JButton button = new JButton(seatNumber);
                button.setBackground(Color.GREEN);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	//버튼이 눌릴 때마다 좌석 색깔 변화 + 예약 완료 메세지
                        button.setBackground(Color.RED);
                        reservationLabel.setText(" 좌석 " + seatNumber + " 예약 완료!");
                    }
                });
                seatPanel.add(button);
            }
        
        //rightPanel에 text 메세지와 seatPanel 추가
        rightPanel.add(rightText, BorderLayout.NORTH);
        rightPanel.add(seatPanel, BorderLayout.CENTER);

        return rightPanel;
    }

    public static void main(String[] args) {
        new MovieReservation();
    }
}