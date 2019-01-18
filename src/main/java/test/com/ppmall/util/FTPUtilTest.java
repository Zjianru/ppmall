package test.com.ppmall.util; 

import com.google.common.collect.Lists;
import com.ppmall.util.FTPUtil;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.File;

/** 
* FTPUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>一月 18, 2019</pre> 
* @version 1.0 
*/ 
public class FTPUtilTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getFtpIp() 
* 
*/ 
@Test
public void testGetFtpIp() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setFtpIp(String ftpIp) 
* 
*/ 
@Test
public void testSetFtpIp() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getFtpUser() 
* 
*/ 
@Test
public void testGetFtpUser() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setFtpUser(String ftpUser) 
* 
*/ 
@Test
public void testSetFtpUser() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getFtpPass() 
* 
*/ 
@Test
public void testGetFtpPass() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setFtpPass(String ftpPass) 
* 
*/ 
@Test
public void testSetFtpPass() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getIp() 
* 
*/ 
@Test
public void testGetIp() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setIp(String ip) 
* 
*/ 
@Test
public void testSetIp() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getPort() 
* 
*/ 
@Test
public void testGetPort() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setPort(int port) 
* 
*/ 
@Test
public void testSetPort() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getUser() 
* 
*/ 
@Test
public void testGetUser() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setUser(String user) 
* 
*/ 
@Test
public void testSetUser() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getPwd() 
* 
*/ 
@Test
public void testGetPwd() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setPwd(String pwd) 
* 
*/ 
@Test
public void testSetPwd() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getFtpClient() 
* 
*/ 
@Test
public void testGetFtpClient() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setFtpClient(FTPClient ftpClient) 
* 
*/ 
@Test
public void testSetFtpClient() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: uploadFile(String remotePath, List<File>fileList) 
* 
*/ 
@Test
public void testUploadFile() throws Exception {
    File file = new File("D:/test.txt");
    FTPUtil.uploadFile(Lists.newArrayList(file));

/* 
try { 
   Method method = FTPUtil.getClass().getMethod("uploadFile", String.class, List<File>fileList.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: connectServer(String ip, int port, String user, String pwd) 
* 
*/ 
@Test
public void testConnectServer() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = FTPUtil.getClass().getMethod("connectServer", String.class, int.class, String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
