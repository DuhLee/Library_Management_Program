package lmp.members.menu.readingroom.usagelist.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import lmp.login.MemberLoginFrame;
import lmp.members.dao.MemberDao;
import lmp.members.dao.MemberLogHistoryDao;
import lmp.members.dao.SeatUseDetailDao;
import lmp.members.menu.readingroom.ReadingRoomPanel;
import lmp.members.menu.readingroom.seatlist.SeatListPanel;
import lmp.members.vo.MemberLogHistoryVO;
import lmp.members.vo.MemberVO;
import lmp.members.vo.SeatUseDetailVO;

public class CheckOutMouseAdapter extends MouseAdapter {

	SeatUseDetailDao sudDao = new SeatUseDetailDao();
	SeatUseDetailVO sudVO;
	MemberLogHistoryDao memLogDao = new MemberLogHistoryDao();
	MemberDao memberDao = new MemberDao();
	MemberLogHistoryVO memLogVO;
	MemberVO memberVO;
	MemberLoginFrame memLogFrame = new MemberLoginFrame();
	ReadingRoomPanel readingRoomPanel;
	
	
	public CheckOutMouseAdapter(ReadingRoomPanel readingRoomPanel) {
		this.readingRoomPanel = readingRoomPanel; 
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		try {
				memLogVO = memLogDao.getLog();
				// 로그인 여부 확인
				if (memLogVO == null) {
					JOptionPane.showMessageDialog(null, "로그인 후 이용하세요.", "Message", 0);
					memLogFrame.initialize();
					memLogFrame.setVisible(true);
				} else {
					memberVO = memberDao.getNum(memLogVO.getMem_num());
					sudVO = sudDao.getUsingInfo(memLogVO.getMem_num());
					
					// 이용중인 자리 확인
					if (sudVO == null) {
						JOptionPane.showMessageDialog(null, "사용중인 자리가 없습니다.", "Message", 0);
					} else {
						if (JOptionPane.showConfirmDialog(null, String.format("좌석번호 : %d\n회원이름 : %s\n시작시간 : %s\n퇴실하시겠습니까?",
								sudVO.getReadingroom().getSeatNum(),
								sudVO.getMember().getName(),
								sudVO.getStartTime().substring(11)), "열람실 자리 발권 확인", JOptionPane.YES_NO_OPTION) == 0) 
							{
							
								sudDao.update(sudVO);
								sudVO = sudDao.getCheckOutInfo(sudVO.getUse_id());
								JOptionPane.showMessageDialog(null, String.format("좌석번호 : %d\n회원이름 : %s\n시작시간 : %s\n종료시간 : %s",
										sudVO.getReadingroom().getSeatNum(),
										sudVO.getMember().getName(),
										sudVO.getStartTime().substring(11),
										sudVO.getEndTime().substring(11)),"퇴실확인",
										JOptionPane.OK_OPTION);
								
								readingRoomPanel.refresh();
						}
					}
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
