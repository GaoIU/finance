package com.fanteng.util;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {

	/**
	 * 导出Excel
	 * 
	 * @param response
	 * @param fileName   文件名称
	 * @param datas      结果集
	 * @param keys       结果集中要取得属性字段
	 * @param columNames 表头显示文字
	 * @param replace    要替换的值
	 * @throws Exception
	 */
	public static void export(HttpServletResponse response, String fileName, List<?> datas, String keys,
			String columNames, String replace) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>(0);
		for (Object obj : datas) {
			Map<String, Object> map = BeanUtil.toMapIncludes(obj, keys);

			if (StringUtil.isNotBlank(replace)) {
				String[] reps = replace.split(";");
				for (String rep : reps) {
					String[] res = rep.trim().split(":");
					String repKey = res[0].trim();
					String oldVal = MapUtils.getString(map, repKey);

					String[] vals = res[1].trim().split(",");
					for (String val : vals) {
						String[] v = val.trim().split("-");
						if (StringUtil.equals(oldVal, v[0])) {
							map.put(repKey, v[1]);
						}
					}
					String val = vals[0];
					String repVal = vals[1];

					if (StringUtil.equals(oldVal, val)) {
						map.put(repKey, repVal);
					}
				}
			}

			list.add(map);
		}

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet(fileName);
		sheet.setDefaultColumnWidth(20);

		String[] keySP = keys.split(",");
		for (int i = 0; i < keySP.length; i++) {
			sheet.setColumnWidth(i, 35 * 150);
		}

		Row tilRow = sheet.createRow(0);
		tilRow.setHeightInPoints(30);

		CellStyle tilCS = wb.createCellStyle();
		Font tilFont = wb.createFont();
		tilFont.setFontHeightInPoints((short) 15);
		tilFont.setColor(IndexedColors.BLACK.getIndex());
		tilFont.setBold(true);
		tilCS.setFont(tilFont);
		tilCS.setBorderLeft(BorderStyle.THIN);
		tilCS.setBorderRight(BorderStyle.THIN);
		tilCS.setBorderTop(BorderStyle.THIN);
		tilCS.setBorderBottom(BorderStyle.THIN);
		tilCS.setAlignment(HorizontalAlignment.CENTER);

		CellStyle valCS = wb.createCellStyle();
		Font valFont = wb.createFont();
		valFont.setFontHeightInPoints((short) 15);
		valFont.setColor(IndexedColors.BLACK.getIndex());
		valFont.setBold(false);
		valCS.setFont(valFont);
		valCS.setBorderLeft(BorderStyle.THIN);
		valCS.setBorderRight(BorderStyle.THIN);
		valCS.setBorderTop(BorderStyle.THIN);
		valCS.setBorderBottom(BorderStyle.THIN);
		valCS.setAlignment(HorizontalAlignment.CENTER);

		String[] columNameSP = columNames.split(",");
		for (int i = 0; i < columNameSP.length; i++) {
			Cell cell = tilRow.createCell(i);
			cell.setCellValue(columNameSP[i].trim());
			cell.setCellStyle(tilCS);
		}

		for (int i = 0; i < list.size(); i++) {
			Row valRow = sheet.createRow(i + 1);
			valRow.setHeightInPoints(35);
			Map<String, Object> map = list.get(i);

			for (int j = 0; j < keySP.length; j++) {
				Cell cell = valRow.createCell(j);
				Object obj = map.get(keySP[j].trim());
				if (obj == null) {
					obj = new String("");
				}

				if (obj instanceof Double || obj instanceof BigDecimal) {
					DecimalFormat df = new DecimalFormat("#.##");
					obj = df.format(obj);
				}

				if (obj instanceof Timestamp) {
					obj = obj.toString().replace(".0", "");
				}

				cell.setCellValue(obj.toString());
				cell.setCellStyle(valCS);
			}
		}

		fileName = URLEncoder.encode(fileName + ".xls", "UTF8");
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.setContentType("application/octet-stream;charset=utf-8");
		OutputStream os = response.getOutputStream();
		wb.write(os);
		wb.close();
		os.flush();
		os.close();
	}

}
