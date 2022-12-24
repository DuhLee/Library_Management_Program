package lmp.members.menu.readingroom.seatlist.panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JPanel;

import lmp.admin.dao.ReadingRoomDao;
import lmp.admin.dao.SeatUseDetailDao;
import lmp.admin.vo.ReadingRoomVO;
import lmp.admin.vo.SeatUseDetailVO;
import lmp.members.menu.readingroom.ReadingRoomPanel;
import lmp.members.menu.readingroom.seatlist.label.SeatLabel;


public class SeatPanel extends JPanel{

	static GridLayout gridLayout = new GridLayout(5,2,3,3);
	static SeatLabel[] seatLabels = new SeatLabel[gridLayout.getRows() * gridLayout.getColumns()];

	ReadingRoomPanel readingRoomPanel;
	int tens;
	
	
	public SeatPanel(ReadingRoomPanel readingRoomPanel, ArrayList<ReadingRoomVO> seatList, ArrayList<SeatUseDetailVO> sudVOs,int tensDigit) throws SQLException {
		this.setLayout(gridLayout);
		this.setBackground(new Color(126, 151, 148));
		this.readingRoomPanel = readingRoomPanel;
		this.tens = tensDigit * 10;
		
		for (int i = 0; i < gridLayout.getRows() * gridLayout.getColumns(); i++) {
			
			seatLabels[i] = new SeatLabel(readingRoomPanel,seatList.get(i + tens));
			add(seatLabels[i]);
		}

		for (int i = 0; i < seatLabels.length; i++) {
			seatLabels[i].setBackground(Color.WHITE);
		}
		
		for (SeatUseDetailVO sudVO : sudVOs) {
			int usageSeatNum = sudVO.getReadingroom().getSeatNum();
			String sex = sudVO.getMember().getSex();
			if (usageSeatNum >= tens + 1 && usageSeatNum <= tens + 10) {
				if (sex.equals("0")) {					
					seatLabels[usageSeatNum - tens - 1].setBackground(new Color(153,204,255));
				} else {
					seatLabels[usageSeatNum - tens - 1].setBackground(new Color(255,153,204));
				}
			}
		}
	}
	
	public void refresh(ArrayList<SeatUseDetailVO> sudVOs) throws SQLException {
		for (int i = 0; i < seatLabels.length; i++) {
			seatLabels[i].setBackground(Color.WHITE);
		}
		
		for (SeatUseDetailVO sudVO : sudVOs) {
			int usageSeatNum = sudVO.getReadingroom().getSeatNum();
			String sex = sudVO.getMember().getSex();
			if (usageSeatNum >= tens + 1 && usageSeatNum <= tens + 10) {
				if (sex.equals("0")) {					
					seatLabels[usageSeatNum - tens - 1].setBackground(new Color(153,204,255));
				} else {
					seatLabels[usageSeatNum - tens - 1].setBackground(new Color(255,153,204));
				}
			}
		}
		
		this.validate();
		
	}
}