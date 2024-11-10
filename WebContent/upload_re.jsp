<%@ page import="java.util.*"%>
<%@ page import="com.util.*"%>
<%
String path = request.getContextPath();
%>
<%
request.setCharacterEncoding("utf-8"); 
response.setCharacterEncoding("utf-8"); 
%>
<%
    
    String newFile1Name=null;
    String file_name=null;
    
	SmartUpload mySmartUpload = new SmartUpload();

	//��ʼ���ϴ�
	mySmartUpload.initialize(pageContext);

	//ֻ�������ش����ļ�
	try 
	{
		mySmartUpload.setAllowedFilesList("jpg,png,jpeg,gif");
		mySmartUpload.upload();
	} 
	catch (Exception e)
    {
		out.println("<script language=javascript>alert('�ϴ���ʽ����');   history.back(-1);</script>");
		return;
	}
	
	try 
	{
		SmartFile myFile = mySmartUpload.getFiles().getFile(0);
		   if (myFile.isMissing())
		   {
			  out.println("<script language=javascript>alert('����ѡ���ļ���');   history.back(-1);</script>");
			  return;

		   } 
		   else 
		   {
			   int file_size = myFile.getSize(); //ȡ���ļ��Ĵ�С (��λ��b) 
			   file_name=myFile.getFileName();
			  // System.out.println("�ļ���С��"+file_size+"�ļ����ƣ�"+file_name);
			   //if (file_size > 10*1024*1024)
			   //{
				  //out.println("<script language=javascript>alert('�ϴ�ͼƬ��СӦ������10K~1M֮�䣡');   history.back(-1);</script>");
				  //return;
			   //}
               //else
               //{
                   newFile1Name=new Date().getTime()+file_name.substring(file_name.indexOf("."));
                   //newFile1Name=file_name;
	               //System.out.println("���ļ����ƣ�"+newFile1Name);
				
				   String saveurl = request.getSession().getServletContext().getRealPath("upload");
				
				   saveurl = saveurl+"/"+newFile1Name;
				   //System.out.println("saveurl==="+saveurl);
				   myFile.saveAs(saveurl, mySmartUpload.SAVE_PHYSICAL);
	
              // }
			} 
	  } 
	  catch (Exception e)
	  {
	    e.toString();
	  }
%>
<script type="text/javascript" src="/ssm_zxncpshop/layer/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="/ssm_zxncpshop/layer/layer.js"></script>
<script language="javascript">

    var str=location.toString()
    var Result=((((str.split('?'))[1]).split('='))[1]);
	//window.opener.Form1(Result).focus();	
	//window.parent.document.getElementById(Result).value="upload/<%= newFile1Name%>";	
	window.parent.document.getElementById(Result).value="<%= newFile1Name%>";					

    
	//window.opener=null;
	//document.write("�ϴ��ɹ�");
    //window.close();
   parent.layer.closeAll();

</script> 