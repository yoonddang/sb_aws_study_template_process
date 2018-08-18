package com.template.process.schedule;

import com.template.common.collect.AbstractBaseCollect;
import com.template.common.mail.MailService;
import com.template.common.model.batch.BatchConstants;
import com.template.common.model.batch.BatchLog;
import com.template.common.model.batch.DataVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.template.common.util.TimeUtil.getNowDateString;
import static com.template.common.util.TimeUtil.getNowDateToString;


public class DataCollect extends AbstractBaseCollect<DataVO> {
	private final Logger logger = LoggerFactory.getLogger(DataCollect.class);

	private DataDAO DataDAO;

	private boolean readTarget = false;
	private String date;
	private String hour;

	protected DataCollect(DataDAO DataDAO, BatchLogDAO batchLogDAO, MailService mailService) {
		super(batchLogDAO, mailService);
		this.DataDAO = DataDAO;
	}

	private void onReadTarget(String date, String hour) {
		this.date = date;
		this.hour = hour;
		this.readTarget = true;
	}

	private void offReadTarget() {
		this.readTarget = false;
	}

	@Override
	protected List<DataVO> read() throws IOException {
		if(readTarget) {
			return read(this.date, this.hour);
		} else {
			return read(getNowDateString(), getNowDateToString("HH"));
		}
	}

	protected List<DataVO> read(String date, String hour) throws IOException {
		/**
		 * return DataDAO.조회메소드
		 *
		 * 크롤링을 하게된다면 이곳에 관련 메소드 혹은 클래스 호출
		 * 로우 데이터 생성
		 */
	}

	@Override
	protected List<DataVO> process(List<DataVO> list) throws IOException {
		/**
		 * 쓰기 전처리 로직
		 *
		 * 크롤링된 로우 데이터를 여기서 가공하면 좋겠죠.
		 * 혹은 관련 클래스에 가공 메소드를 작성하여 여기서 호출 하여도 굳.
		 */

		return super.process(list);
	}

	@Override
	public int write(DataVO data) throws IOException {
		return dataDAO.insertData(data);
	}

	@Override
	protected int bulkWrite(List<DataVO> data) throws IOException {
		if(data == null || data.isEmpty()) {
			return 0;
		} else {
			return dataDAO.insertDataBulk(data);
		}
	}

	@Override
	protected boolean setAfter() throws IOException {
		/**
		 * 후처리 로직
		 */
		boolean check = false;

		return check;
	}

	//@Scheduled(cron = "0 15/20 7-23 * * ?")
	public void dataTask() {
		offReadTarget();
		setJobName(BatchConstants.JOB_DATA);
		setBulk(false);
		doCollectTask();
	}

	//@Scheduled(cron = "0 20/20 7-23 * * ?")
	//@Scheduled(cron = "0 34 * * * ?")
	public void bulkDataTask() {
		offReadTarget();
		setJobName(BatchConstants.JOB_DATA);
		setBulk(true);
		doCollectTask();
	}

}
