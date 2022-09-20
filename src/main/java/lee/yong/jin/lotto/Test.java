package lee.yong.jin.lotto;

public class Test {

	public static void main(String[] args) {
		scrap();
	}
	
	public static void scrap() throws Exception{
		int[] arr = {14,15,18,19,20,21,22,23,24};
		
		for(int i=0; i < arr.length; i++ ) {
			String url = "http://www.namastte.kr/popup.php?s=step01&t=resve&innb=5b7d0fe8da05f5b7d0fe8da0a1&Y=2022&m=10&searchRoomTy=" + arr[i];
			//System.out.println("url:" + url);
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String inputLine;
	        StringBuffer response = new StringBuffer();
	 
	        while ((inputLine = reader.readLine()) != null) {
	            response.append(inputLine);
	        }
	        reader.close();
	        
//	        System.out.println(response.toString());
	        httpClient.close();
	        
	        Document doc = Jsoup.parseBodyFragment(response.toString());
	        Element body = doc.body();
	        Elements calendar_style_01 = body.getElementsByClass("calendar_style_01");
	        Element td = calendar_style_01.get(0).getElementsByTag("table").get(0).getElementsByTag("tbody").get(0).getElementsByTag("tr").get(0).getElementsByTag("td").get(6);
	        Elements possibles = td.select("span.icon_possible"); //icon_possible
	        for (Element possible : possibles) {
	        	System.out.println("예약가능!!!" + possible.html());
			}
	        Thread.sleep(1*1000);
		}
	}
}
