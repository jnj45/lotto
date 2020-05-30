package lee.yong.jin.lotto;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.h2.util.CurrentTimestamp;
import org.h2.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SpringBootTest
class LottoApplicationTests {

	List<Map> dataList;

	@Test
	void contextLoads() {
		dataList = loadExcel();
		/*for (Map data : dataList) {
			System.out.println(data);
		}*/

		boolean isOk = true;
		/*
		//첫번째 번호
		int no1 = 0;
		while(isOk){
			no1 = RandomUtils.nextInt(1, 15);
			isOk = !checkExistRecent(1, no1);
		}
		System.out.println("no1:"+no1);

		//2번째번호
		isOk = true;
		int no2 = 0;
		while(isOk){
			no2 = RandomUtils.nextInt(no1+1, 25);
			isOk = !checkExistRecent(2, no2) && !checkExistAll(no1+","+no2);
		}
		System.out.println("no2:"+no2);
*/
		int no1 = 1; int no2 = 0;
		while(isOk){
			int a = RandomUtils.nextInt(1, 10);
			int b = RandomUtils.nextInt(11, 25);
			if (a < b){
				no1 = a; no2 = b;
			}else{
				no1 = b; no2 = a;
			}
			isOk = a!=b && !checkExistAll(no1+","+no2);
		}
		System.out.println("no1:"+no1);
		System.out.println("no2:"+no2);

		//3번째번호
		isOk = true;
		int no3 = 0;
		while(isOk){
			no3 = RandomUtils.nextInt(no2+1, 35);
			isOk = !checkExistRecent(3, no3) && !checkExistAll(no1+","+no2+","+no3);
		}
		System.out.println("no3:"+no3);

		//4번째번호
		isOk = true;
		int no4 = 0;
		while(isOk){
			no4 = RandomUtils.nextInt(no3+1, 40);
			isOk = !checkExistRecent(4, no4) && !checkExistAll(no2+","+no3+","+no4);
		}
		System.out.println("no4:"+no4);

		//5번째번호
		isOk = true;
		int no5 = 0;
		while(isOk){
			no5 = RandomUtils.nextInt(no4+1, 45);
			isOk = !checkExistRecent(5, no5) && !checkExistAll(no3+","+no4+","+no5);
		}
		System.out.println("no5:"+no5);

		//6번째번호
		isOk = true;
		int no6 = 0;
		while(isOk){
			no6 = RandomUtils.nextInt(no5+1, 45);
			isOk = !checkExistRecent(6, no6) && !checkExistAll(no4+","+no5+","+no6);
		}
		System.out.println("no6:"+no6);

	}

	@SuppressWarnings("unchecked")
	List<Map> loadExcel() {
		List<Map> dataList = new ArrayList<Map>();

		try {
			FileInputStream file = new FileInputStream("C:\\Users\\jnj45\\OneDrive\\문서\\lotto_numbers.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex=0;
			int columnindex=0;
			//시트 수 (첫번째에만 존재하므로 0을 준다)
			//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
			XSSFSheet sheet=workbook.getSheetAt(0);
			//행의 수
			int rows=sheet.getPhysicalNumberOfRows();

			for(rowindex=3;rowindex<rows;rowindex++){
				//행을읽는다
				XSSFRow row=sheet.getRow(rowindex);
				if(row !=null){
					Map data = new HashMap();
					data.put("회차",  (int)row.getCell(1).getNumericCellValue());
					data.put("번호1", (int)row.getCell(13).getNumericCellValue());
					data.put("번호2", (int)row.getCell(14).getNumericCellValue());
					data.put("번호3", (int)row.getCell(15).getNumericCellValue());
					data.put("번호4", (int)row.getCell(16).getNumericCellValue());
					data.put("번호5", (int)row.getCell(17).getNumericCellValue());
					data.put("번호6", (int)row.getCell(18).getNumericCellValue());

					data.put("번호들",   MapUtils.getIntValue(data, "번호1") + "," +
										MapUtils.getIntValue(data, "번호2") + "," +
										MapUtils.getIntValue(data, "번호3") + "," +
										MapUtils.getIntValue(data, "번호4") + "," +
										MapUtils.getIntValue(data, "번호5") + "," +
										MapUtils.getIntValue(data, "번호6"));
					data.put("총합", MapUtils.getIntValue(data, "번호1") +
									MapUtils.getIntValue(data, "번호2") +
									MapUtils.getIntValue(data, "번호3") +
									MapUtils.getIntValue(data, "번호4") +
									MapUtils.getIntValue(data, "번호5") +
									MapUtils.getIntValue(data, "번호6"));
					System.out.println(data);
					dataList.add(data);

					//셀의 수
					/*int cells=row.getPhysicalNumberOfCells();
					for(columnindex=0; columnindex<=cells; columnindex++){
						//셀값을 읽는다
						XSSFCell cell=row.getCell(columnindex);
						String value="";
						//셀이 빈값일경우를 위한 널체크
						if(cell==null){
							continue;
						}else{
							//타입별로 내용 읽기
							switch (cell.getCellType()){
								case XSSFCell.CELL_TYPE_FORMULA:
									value=cell.getCellFormula();
									break;
								case XSSFCell.CELL_TYPE_NUMERIC:
									value=cell.getNumericCellValue()+"";
									break;
								case XSSFCell.CELL_TYPE_STRING:
									value=cell.getStringCellValue()+"";
									break;
								case XSSFCell.CELL_TYPE_BLANK:
									value=cell.getBooleanCellValue()+"";
									break;
								case XSSFCell.CELL_TYPE_ERROR:
									value=cell.getErrorCellValue()+"";
									break;
							}
						}
						System.out.println(rowindex+"번 행 : "+columnindex+"번 열 값은: "+value);
					}*/

				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("총건수:" + dataList.size());
		return dataList;
	}

	boolean checkExistRecent(int no, int src){
		int recentIndex = 10;
		for(int i=0; i < recentIndex; i++){
			Map data = dataList.get(i);
			if (src == MapUtils.getIntValue(data, "번호"+no)){
				return true;
			}
		}
		return false;
	}

	boolean checkExistAll(String str){
		for (Map data: dataList) {
			String dataStr = ","+MapUtils.getString(data, "번호들")+",";
			if (dataStr.indexOf(str) > 0){
				return true;
			}
		}
		return false;
	}

}
