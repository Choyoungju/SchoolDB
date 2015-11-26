package db1;

import java.sql.Connection; 
import java.sql.DatabaseMetaData;
import java.sql.DriverManager; 
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException; 
import java.sql.Statement; 
public class test { 
	public static void main(String args[]) { 
		String sql;
		int ret;
		String url = "jdbc:mysql://localhost:3306/mycompany"; 
		Connection con = null; //연결객체를 선언만함
		Statement  stmt = null; //전송객체를 선언만함


		try { //mysql과 연동을 위해서 드라이버를 메모리를 올림,에러시 에러출력
			Class.forName("org.gjt.mm.mysql.Driver"); 
		} catch(Exception e) { 
			System.err.print(e.toString()); 
		} 
		try { 
			con = DriverManager.getConnection(url,"root","5630"); 
			//서버 url,db명,비밀번호 연결객체 획득
			stmt = con.createStatement(); 
			//sql에 입력할 데이터를 mysql로 전송하는 객체가 stmt,실질적인 일은 메소드가 수행
			sql ="CREATE TABLE employee (  fname  varchar(15) not null,   minit    varchar(1),  lname    varchar(15) not null,  ssn      char(9),  bdate    date,  address  varchar(30),  sex      char,  salary   decimal(10,2),  superssn char(9),  dno      int(4),  email    varchar(50),  primary key (ssn) );";
			ret = stmt.executeUpdate(sql);
			tableshow(con);

			sql = "CREATE TABLE department (  dname varchar(15) not null,  dnumber int(4),  mgr_ssn   char(9) not null,  mgr_start_date date,  primary key (dnumber),  unique (dname),  foreign key (mgr_ssn) references employee(ssn))";
			ret = stmt.executeUpdate(sql);
			tableshow(con);
			sql = "alter table employee add (  foreign key (dno) references department(dnumber),  foreign key (superssn) references employee(ssn))";
			ret = stmt.executeUpdate(sql);
			tableshow(con);
			sql = "CREATE TABLE dept_locations (  dnumber   int(4),  dlocation varchar(15),  primary key (dnumber,dlocation),  foreign key (dnumber) references department(dnumber))";
			ret = stmt.executeUpdate(sql);
			tableshow(con);
			sql = "CREATE TABLE project (  pname  varchar(15) not null,  pnumber  int(4),  plocation  varchar(15), dnum int(4) not null,  primary key (pnumber), unique (pname),  foreign key (dnum) references department(dnumber))";
			ret = stmt.executeUpdate(sql);
			tableshow(con);
			sql = "CREATE TABLE works_on (  essn   char(9),  pno    int(4),  hours  decimal(4,1),  primary key (essn,pno),  foreign key (essn) references employee(ssn),  foreign key (pno) references project(pnumber))";
			ret = stmt.executeUpdate(sql);
			tableshow(con);
			sql = "CREATE TABLE dependent (  essn   char(9),  dependent_name varchar(15),  sex char,  bdate  date,  relationship varchar(8),  primary key (essn,dependent_name),  foreign key (essn) references employee(ssn))";
			ret = stmt.executeUpdate(sql);
			tableshow(con);



			ret = 0;

			sql=	"INSERT INTO employee VALUES ('James', 'E', 'Borg', '888665555', '1937-11-10', 'Houston,TX', 'M', 55000, null, null, null) ";
			ret += stmt.executeUpdate(sql);
			employeeshow(stmt);
			sql = "INSERT INTO employee VALUES ('Franklin', 'T', 'Wong', '333445555', '1955-12-08', 'Houston,TX', 'M', 40000, '888665555', null, null)";
			ret += stmt.executeUpdate(sql);
			employeeshow(stmt);
			sql = "INSERT INTO employee VALUES ('Jennifer', 'S', 'Wallace', '987654321', '1941-06-20', 'Bellaire,TX', 'F', 43000, '888665555', null, null)";
			ret += stmt.executeUpdate(sql);
			employeeshow(stmt);
			sql = "INSERT INTO employee VALUES ('John', 'B', 'Smith', '123456789', '1965-01-09', 'Houston,TX', 'M', 30000, '333445555', 5, null)";
			ret += stmt.executeUpdate(sql);
			employeeshow(stmt);
			sql = "INSERT INTO employee VALUES ('Alicia', 'J', 'Zelaya', '999887777', '1968-01-19', 'Spring,TX', 'F', 25000, '987654321', 4, null)";
			ret += stmt.executeUpdate(sql);
			employeeshow(stmt);
			sql = "INSERT INTO employee VALUES ('Ramesh', 'K', 'Narayan', '666884444', '1962-09-15', 'Humble,TX', 'M', 38000, '333445555', 5, null)";
			ret += stmt.executeUpdate(sql);
			employeeshow(stmt);
			sql = "INSERT INTO employee VALUES ('Joyce', 'A', 'English', '453453453', '1972-07-31', 'Houston, TX', 'F', 25000, '333445555', 5, null)";
			ret += stmt.executeUpdate(sql);
			employeeshow(stmt);
			sql = "INSERT INTO employee VALUES ('Ahmad', 'V', 'Jabbar', '987987987', '1969-03-29', 'Houston,TX', 'M', 25000, '987654321', 4, null)";
			ret += stmt.executeUpdate(sql);
			employeeshow(stmt);
			System.out.println("레코드 " + ret + "개가 추가되었습니다."); 
			System.out.println("");

			ret = 0;

			sql = "INSERT INTO department VALUES ('Research', 5, '333445555', '1988-05-22')";
			ret += stmt.executeUpdate(sql);
			departmentshow(stmt);
			sql = "INSERT INTO department VALUES ('Administration', 4, '987654321', '1995-01-01')";
			ret += stmt.executeUpdate(sql);
			departmentshow(stmt);
			sql = "INSERT INTO department VALUES ('Headquarters', 1, '888665555', '1981-06-19')";
			ret += stmt.executeUpdate(sql);
			departmentshow(stmt);
			System.out.println("레코드 " + ret + "개가 추가되었습니다.");


			
			ret = 0;

			sql = "INSERT INTO project VALUES ('ProductX', 1, 'Bellaire',  5)";
			ret += stmt.executeUpdate(sql);
			projectshow(stmt);
			sql = "INSERT INTO project VALUES ('ProductY', 2, 'Sugarland', 5)";
			ret += stmt.executeUpdate(sql);
			projectshow(stmt);
			sql = "INSERT INTO project VALUES ('ProductZ', 3, 'Houston', 5)";
			ret += stmt.executeUpdate(sql);
			projectshow(stmt);
			sql = "INSERT INTO project VALUES ('Computerization', 10, 'Stafford', 4)";
			ret += stmt.executeUpdate(sql);
			projectshow(stmt);
			sql = "INSERT INTO project VALUES ('Reorganization', 20, 'Houston', 1)";
			ret += stmt.executeUpdate(sql);
			projectshow(stmt);
			sql = "INSERT INTO project VALUES ('Newbenefits', 30,  'Stafford', 4)";
			ret += stmt.executeUpdate(sql);
			projectshow(stmt);
			System.out.println("레코드 " + ret + "개가 추가되었습니다.");

			ret = 0;


			sql = "INSERT INTO dept_locations VALUES (1, 'Houston')";
			ret += stmt.executeUpdate(sql);
			dept_locationsshow(stmt);
			sql = "INSERT INTO dept_locations VALUES (4, 'Stafford')";
			ret += stmt.executeUpdate(sql);
			dept_locationsshow(stmt);
			sql = "INSERT INTO dept_locations VALUES (5, 'Bellaire')";
			ret += stmt.executeUpdate(sql);
			dept_locationsshow(stmt);
			sql = "INSERT INTO dept_locations VALUES (5, 'Sugarland')";
			ret += stmt.executeUpdate(sql);
			dept_locationsshow(stmt);
			sql = "INSERT INTO dept_locations VALUES (5, 'Houston')";
			ret += stmt.executeUpdate(sql);
			dept_locationsshow(stmt);
			System.out.println("레코드 " + ret + "개가 추가되었습니다.");



			
			ret = 0;
			sql = "INSERT INTO dependent VALUES ('333445555','Alice','F','1986-04-05','Daughter')";
			ret += stmt.executeUpdate(sql);
			dependentshow(stmt);
			sql = "INSERT INTO dependent VALUES ('333445555','Theodore','M','1983-10-25','Son')";
			ret += stmt.executeUpdate(sql);
			dependentshow(stmt);
			sql = "INSERT INTO dependent VALUES ('333445555','Joy','F','1958-05-03','Spouse')";
			ret += stmt.executeUpdate(sql);
			dependentshow(stmt);
			sql = "INSERT INTO dependent VALUES ('987654321','Abner','M','1942-02-28','Spouse')";
			ret += stmt.executeUpdate(sql);
			dependentshow(stmt);
			sql = "INSERT INTO dependent VALUES ('123456789','Michael','M','1988-01-04','Son')";
			ret += stmt.executeUpdate(sql);
			dependentshow(stmt);
			sql = "INSERT INTO dependent VALUES ('123456789','Alice','F', '1988-12-30','Daughter')";
			ret += stmt.executeUpdate(sql);
			dependentshow(stmt);
			sql = "INSERT INTO dependent VALUES ('123456789','Elizabeth','F','1967-05-05','Spouse')";
			ret += stmt.executeUpdate(sql);
			dependentshow(stmt);
			System.out.println("레코드 " + ret + "개가 추가되었습니다.");
    
			ret = 0;
			sql = "INSERT INTO works_on VALUES ('123456789', 1,  32.5)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('123456789', 2,  7.5)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('666884444', 3,  40.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('453453453', 1,  20.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('453453453', 2,  20.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('333445555', 2,  10.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('333445555', 3,  10.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('333445555', 10, 10.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('333445555', 20, 10.0)";
			ret += stmt.executeUpdate(sql);     
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('999887777', 30, 30.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('999887777', 10, 10.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('987987987', 10, 35.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('987987987', 30, 5.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('987654321', 30, 20.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('987654321', 20, 15.0)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			sql = "INSERT INTO works_on VALUES ('888665555', 20, null)";
			ret += stmt.executeUpdate(sql);
			works_onshow(stmt);
			System.out.println("레코드 " + ret + "개가 추가되었습니다.");


			


			
//			ResultSet rs7 = stmt.executeQuery
//					("SELECT bdate,address from employee where fname='John' and minit = 'B' and lname='Smith'");
//			System.out.println( " Queary 0" );
//			System.out.println("bdate  address");
//
//			String fname, bdate, minit, lname ,address, dname;
//			while (rs7.next()) {
//
//				bdate = rs7.getString("bdate");
//				
//				address = rs7.getString("address");
//
//
//				System.out.println(bdate + " " + address);
//
//			}
//			System.out.println("--------------------------");
//			System.out.println(""); 
//		
//			ResultSet rs8 = stmt.executeQuery
//					("SELECT  fname, lname,address from employee, department where dname='Research' and dnumber=dno");
//
//			System.out.println( " Queary 1" );
//			System.out.println("fname lname address");
//			while (rs8.next()) {
//
//				fname = rs8.getString("fname");
//				lname = rs8.getString("lname");
//				//  dname = rs8.getString("dname");
//				//  int dnumber = rs8.getInt("dnumber");
//
//				address = rs8.getString("address");
//
//
//				System.out.println(fname + " " + lname+ " " + address);
//
//			}
//			System.out.println("--------------------------");
//			System.out.println(""); 

			System.out.println("");

			ResultSet rs7 = stmt.executeQuery
					("SELECT * from department ORDER by department.dname desc");
			System.out.println( " Queary 4" );
			System.out.println( "SELECT * from department ORDER by department.dname desc" );
			System.out.println("dname dnumber mgr_ssn mgr_start_date ");
			System.out.println("--------------------------");
		

			String dname, mgr_ssn, mgr_start_date;
			int dnumber;
			while (rs7.next()) {

				dname = rs7.getString("dname");
				
				dnumber = rs7.getInt("dnumber");
				mgr_ssn = rs7.getString("mgr_ssn");
				mgr_start_date = rs7.getString("mgr_start_date");


				System.out.println(dname + " " + dnumber+ " " +  mgr_ssn + " " + mgr_start_date);

			}
			System.out.println("--------------------------");
			System.out.println("");
			
			
			System.out.println("");

			 rs7 = stmt.executeQuery
					("SELECT * from Employee ORDER by lname asc");
			System.out.println( " Queary 4" );
			System.out.println( "SELECT * from Employee ORDER by lname asc");
			System.out.println("fname minit lname ssn bdate address sex salary super_ssn dno");
			System.out.println("--------------------------");
		

			String fname, minit, lname, ssn ,bdate ,address ,sex, salary ,superssn ;
			int dno;
			while (rs7.next()) {

				fname = rs7.getString("fname");
				minit = rs7.getString("minit");
				lname = rs7.getString("lname");
				ssn = rs7.getString("bdate");
				bdate = rs7.getString("ssn");
				address = rs7.getString("address");
				sex = rs7.getString("sex");
				salary = rs7.getString("salary");
				superssn = rs7.getString("superssn");
				dno = rs7.getInt("dno");
				


				System.out.println(fname + " " + minit+ " " +  lname + " " + bdate + " " + address+ " " +sex + " " +salary + " " + superssn+ " " + dno);

			}
			System.out.println("--------------------------");
			System.out.println("");
			
			
			System.out.println("");

			 rs7 = stmt.executeQuery
					("SELECT * from Employee ORDER by fname asc");
			System.out.println( " Queary 4" );
			System.out.println( " SELECT * from Employee ORDER by fname asc" );
			System.out.println("fname minit lname ssn bdate address sex salary super_ssn dno");
			System.out.println("--------------------------");
		
		
			while (rs7.next()) {

				fname = rs7.getString("fname");
				minit = rs7.getString("minit");
				lname = rs7.getString("lname");
				ssn = rs7.getString("bdate");
				bdate = rs7.getString("ssn");
				address = rs7.getString("address");
				sex = rs7.getString("sex");
				salary = rs7.getString("salary");
				superssn = rs7.getString("superssn");
				dno = rs7.getInt("dno");
				


				System.out.println(fname + " " + minit+ " " +  lname + " " + bdate + " " + address+ " " +sex + " " +salary + " " + superssn+ " " + dno);

			}
			System.out.println("--------------------------");
			System.out.println("");
			
			
			
			System.out.println("");

			 rs7 = stmt.executeQuery
					 ("SELECT  sum(salary) as salary1, max(salary) as salary2 , min(salary) as salary3, avg(salary) as salary4 from employee, department where dname='Research' and dnumber=dno");
			System.out.println( " Queary 20" );
						
			//("SELECT  sum(salary), max(salary), min(salary), avg(salary)  from employee, department where dname='Research' and dnumber=dno");
			System.out.println("sum(salary), max(salary), min(salary), avg(salary)");
			System.out.println("--------------------------");
		
String sum, max , min, avg;
			while (rs7.next()) {

				sum = rs7.getString("salary1");
				max = rs7.getString("salary2");
				min = rs7.getString("salary3");
				avg = rs7.getString("salary4");
				
				
				


				System.out.println(sum + " "  + max+ " "  + min + " "  + avg);

			}
			System.out.println("--------------------------");
			System.out.println("");
			
			
			rs7 = stmt.executeQuery
					 ("SELECT  count(*) as count from employee");
			System.out.println( " Queary 21" );
						
			//("SELECT  sum(salary), max(salary), min(salary), avg(salary)  from employee, department where dname='Research' and dnumber=dno");
			System.out.println("SELECT  count(*) as count from employee");
			System.out.println("--------------------------");
		
String count;
			while (rs7.next()) {

				count = rs7.getString("count");

				System.out.println(count);

			}
			System.out.println("--------------------------");
			System.out.println("");
			
			
			
			rs7 = stmt.executeQuery
					 ("SELECT  count(*) as count from employee,department where dno=dnumber and dname='Research'");
			System.out.println( " Queary 22" );
						
			//("SELECT  sum(salary), max(salary), min(salary), avg(salary)  from employee, department where dname='Research' and dnumber=dno");
			System.out.println("SELECT  count(*) as count from employee,department where dno=dnumber and dname='Research'");
			System.out.println("--------------------------");
		

			while (rs7.next()) {

				count = rs7.getString("count");

				System.out.println(count);

			}
			System.out.println("--------------------------");
			System.out.println("");
			
			
			rs7 = stmt.executeQuery
					 ("SELECT  dnumber, count(*) as count from department, employee where dnumber =dno and salary>40000 and (select dno from employee group by dno having count(*)  > 5) ");
			System.out.println( " Queary 24" );
						
			//("SELECT  sum(salary), max(salary), min(salary), avg(salary)  from employee, department where dname='Research' and dnumber=dno");
			System.out.println("SELECT  dnumber, count(*) as count from department, employee where dnumber =dno and salary>40000 and (select dno from employee group by dno having count(*)  > 5) ");
			System.out.println("--------------------------");
		

			while (rs7.next()) {

				count = rs7.getString("count");

				System.out.println(count);

			}
			System.out.println("--------------------------");
			System.out.println("");
			
			
//
//			ResultSet rs9 = stmt.executeQuery
//					("SELECT  pnumber, dnum, lname, address, bdate FROM project,department,employee WHERE  dnum=dnumber and mgr_ssn=ssn and plocation ='Stafford'");
//			int pnumber;
//			System.out.println( " Queary 2" );
//			System.out.println("pnumber dnum lname  address  bdate");
//			while (rs9.next()) {
//
//				pnumber = rs9.getInt("pnumber");
//				lname = rs9.getString("lname");
//				bdate = rs9.getString("bdate");
//				int dnum = rs9.getInt("dnum");
//				//  int dnumber = rs9.getInt("dnumber");
//				address = rs9.getString("address");
//				//  String mgr_ssn = rs9.getString("mgr_ssn");
//				//  String ssn = rs9.getString("ssn");
//				//  String plocation = rs9.getString("plocation");
//
//
//				System.out.println(pnumber + " " + dnum+ " " + lname +" " + address + " " + bdate);
//
//			}



		} catch(Exception e) { 
			System.out.println("Exception: " + e.getMessage()); 
		} finally{ //서버객체,연결객체가 null값이 아닐 때 예외처리로 연결을 끊어주는 소스가 필요
			try{  //즉 100명이db에 접속되어있는데 계속100명이접속하는데 미리접속한사람은 그대로 유지 시
				//포화가 된 db서버가 멈춰버릴 수 있음,그래서 사용한 객체,전송은 닫아줘야함
				if ( stmt != null){ stmt.close(); } 
			}catch(Exception e){} 
			try{ 
				if ( con != null){ con.close(); } 
			}catch(Exception e){}  }}





	public static void employeeshow(Statement stmt){
		try{
			ResultSet rs1 = stmt.executeQuery
					("SELECT * FROM employee");
			System.out.println("fname Minit lname ssn Bdate Address sex salary super_ssn Dno");
			System.out.println("--------------------------");
			while (rs1.next()) {

				String fname = rs1.getString("fname");
				String minit = rs1.getString("minit");
				String lname = rs1.getString("lname");
				String ssn = rs1.getString("ssn");
				String bdate = rs1.getString("bdate");
				String address = rs1.getString("address");
				String sex = rs1.getString("sex");
				String salary = rs1.getString("salary");
				String super_ssn = rs1.getString("ssn");
				int  dno = rs1.getInt("dno");

				System.out.println(fname + " " + minit + " " +  lname + " " +  ssn + " " +  bdate + " " +  address + " " +  sex + " " +  salary + " " +  super_ssn + " " +  dno);

			}
			
			System.out.println("");
		} catch(Exception e) { 
			System.out.println("Exception: " + e.getMessage()); 
		}

	}
	
	public static void departmentshow(Statement stmt){
		try{
			ResultSet rs2 = stmt.executeQuery
					("SELECT * FROM department");
			System.out.println("dname dnumber mgr_ssn mgr_start_date");
			System.out.println("--------------------------");
			while (rs2.next()) {

				String dname = rs2.getString("dname");
				int dnumber = rs2.getInt("dnumber");
				String mgr_ssn = rs2.getString("mgr_ssn");
				String mgr_start_date = rs2.getString("mgr_start_date");


				System.out.println(dname + " " + dnumber + " " +  mgr_ssn + " " +  mgr_start_date );

			}

			
			System.out.println("");
		
		} catch(Exception e) { 
			System.out.println("Exception: " + e.getMessage()); 
		}

	}
	
	
	public static void projectshow(Statement stmt){
		try{
			ResultSet rs3 = stmt.executeQuery
					("SELECT * FROM project");
			System.out.println("pname pnumber plocation dnum");
			System.out.println("--------------------------");
			while (rs3.next()) {

				String pname = rs3.getString("pname");
				int pnumber = rs3.getInt("pnumber");
				String plocation = rs3.getString("plocation");
				String dnum = rs3.getString("dnum");


				System.out.println(pname + " " + pnumber + " " +  plocation + " " +  dnum );

			}
		
			System.out.println("");
		
		} catch(Exception e) { 
			System.out.println("Exception: " + e.getMessage()); 
		}

	}
	
	public static void dependentshow(Statement stmt){
		try{

			ResultSet rs5 = stmt.executeQuery
					("SELECT * FROM dependent");
			System.out.println("essn dependent_name sex bdate relationship");
			System.out.println("--------------------------");
			while (rs5.next()) {

				String essn = rs5.getString("essn");
				String dependent_name = rs5.getString("dependent_name");
				String sex = rs5.getString("sex");
				String bdate = rs5.getString("bdate");
				String relationship = rs5.getString("relationship");


				System.out.println(essn + " " + dependent_name + " " +  sex + " " +  bdate + " " +  bdate + " " +  relationship );

			}

			
			System.out.println("");   
			
		
		} catch(Exception e) { 
			System.out.println("Exception: " + e.getMessage()); 
		}

	}
	
	
	
	
	public static void dept_locationsshow(Statement stmt){
		try{
			ResultSet rs4 = stmt.executeQuery
					("SELECT * FROM dept_locations");
			System.out.println("dnumber dlocation");
			System.out.println("--------------------------");
			while (rs4.next()) {

				int dnumber = rs4.getInt("dnumber");
				String dlocation = rs4.getString("dlocation");


				System.out.println(dnumber + " " + dlocation );

			}
			
			System.out.println("");
			
		} catch(Exception e) { 
			System.out.println("Exception: " + e.getMessage()); 
		}

	}
	
	public static void works_onshow(Statement stmt){
		try{
			ResultSet rs6 = stmt.executeQuery
					("SELECT * FROM works_on");
			System.out.println("essn pno hours");
			System.out.println("--------------------------");
			while (rs6.next()) {

				String essn = rs6.getString("essn");
				int pno = rs6.getInt("pno");
				String hours = rs6.getString("hours");


				System.out.println(essn + " " + pno + " " +  hours);

			}

			
			System.out.println(""); 
			
		} catch(Exception e) { 
			System.out.println("Exception: " + e.getMessage()); 
		}

	}

	
	
	
	

	public static void tableshow(Connection con){
		try{
			DatabaseMetaData md = con.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			System.out.println("--------------------------");
			while (rs.next()) {
				System.out.println(rs.getString(3));
			}
			System.out.println("릴레이션 테이블 생성 완료");
		
			System.out.println("");
		}catch(Exception e) { 
			System.out.println("Exception: " + e.getMessage()); 
		}
	}
}
