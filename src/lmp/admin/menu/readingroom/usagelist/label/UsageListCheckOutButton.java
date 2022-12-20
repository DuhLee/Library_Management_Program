package lmp.admin.menu.readingroom.usagelist.label;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import lmp.admin.dao.ReadingRoomDao;
import lmp.admin.dao.SeatUseDetailDao;
import lmp.admin.menu.readingroom.ReadingRoomPanel;
import lmp.admin.menu.readingroom.seatlist.SeatListPanel;
import lmp.admin.menu.readingroom.usagelist.UsageListPanel;
import lmp.admin.menu.readingroom.usagelist.scrollpane.table.UsageListTable;
import lmp.admin.vo.SeatUseDetailVO;

public class UsageListCheckOutButton extends JButton{
	
	
	public UsageListCheckOutButton(ReadingRoomPanel readingRoomPanel) {
		
		System.out.println("checkOUtButton");
		
		this.setText("강제퇴실");
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(Color.GRAY);
		this.setForeground(Color.RED);
		this.setFont(new Font("한컴 말랑말랑 Regular", Font.BOLD, 15));
//		this.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		
		this.addMouseListener(new MouseAdapter() {
			// 버튼에 마우스 올리면 배경색 변경
			@Override
			public void mouseEntered(MouseEvent e) {
				// setFocusPainted(true);
				setContentAreaFilled(true);
			}

			// 버튼에서 마우스 떼면 배경색 투명
			@Override
			public void mouseExited(MouseEvent e) {
				// setFocusPainted(false);
				setContentAreaFilled(false);
			}
		});
		
		SeatListPanel  seatListPanel = readingRoomPanel.getSeatListPanel();
		UsageListPanel usageListPanel = readingRoomPanel.getUsageListPanel();
		UsageListTable usageListTable = readingRoomPanel.getUsageListScrollPane().getUsageListTable();

		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				SeatUseDetailDao sudDao = new SeatUseDetailDao();
				ReadingRoomDao roomDao = new ReadingRoomDao();
				ArrayList<SeatUseDetailVO> sudList =  new ArrayList<>();
				
				// 테이블에 선택한 값이 없는 경우
				System.out.println(usageListTable.getSelectedRow());
				if (usageListTable.getSelectedRow() != -1) {

					if (usageListTable.getValueAt(usageListTable.getSelectedRow(), 0) != null) {
						int result = JOptionPane.showConfirmDialog(usageListPanel, "해당 사용자를 퇴실처리 하시겠습니까?", "강제퇴실 확인",
								JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.NO_OPTION) {
							return;
						} else {

							int selectRow = usageListTable.getSelectedRow();// 선택된 테이블의 행값
							Integer seat_num = (int) usageListTable.getValueAt(selectRow, 0);

							if (seat_num != null) {
								try {
									sudDao.update(seat_num);
								} catch (SQLException e2) {
									e2.printStackTrace();
								}

								// --------- 데이터베이스 수정하기 ---------

								try {
									sudList.addAll(sudDao.get()); // DB 내용 가져오기
									DefaultTableModel model = readingRoomPanel.getUsageListScrollPane().getModel();

									int totalSeat = roomDao.get().size();

									int resetRow = 0;
									for (SeatUseDetailVO sud : sudList) {

										model.setRowCount(sudList.size());
										for (int i = 0; i < sud.getSudList().length; i++) {
											model.setValueAt(sud.getSudList()[i], resetRow, i);
										}
										resetRow++;
									}

									usageListTable.setModel(model);

									usageListTable.validate(); // 새로고침 - 버튼 액션으로

									seatListPanel.refresh(sudList);

								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
						}
					} else {
						JOptionPane.showMessageDialog(usageListTable, "퇴실할 좌석을 선택해 주세요");
						return;
					}
				} else {
					JOptionPane.showMessageDialog(usageListTable, "퇴실할 좌석을 선택해 주세요");
					return;
				}
			}
		});
	}
}