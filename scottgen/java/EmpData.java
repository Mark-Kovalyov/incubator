import jdk.nashorn.internal.ir.annotations.Immutable;
import java.time.LocalDate;
import java.util.Optional;

@Immutable
public class Emp2 {

    public final int empno;
    public final String ename;
    public final String job;
    public final Optional<Integer> mgr;
    public final LocalDate hiredate;
    public final double sal;
    public final Optional<Double> comm;
    public final int deptno;

    public Emp2(int empno, String ename, String job, Optional<Integer> mgr, LocalDate hiredate, double sal, Optional<Double> comm, int deptno) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.hiredate = hiredate;
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
    }
}

List<Emp2> emps = new ArrayList() {{
  add(new Emp2(7369, "SMITH", "CLERK", Optional.of(7902), LocalDate.parse("1980-12-17"), 800.0, Optional.empty(), 20));
  add(new Emp2(7499, "ALLEN", "SALESMAN", Optional.of(7698), LocalDate.parse("1981-02-20"), 1600.0, Optional.of(300.0), 30));
  add(new Emp2(7521, "WARD", "SALESMAN", Optional.of(7698), LocalDate.parse("1981-02-22"), 1250.0, Optional.of(500.0), 30));
  add(new Emp2(7566, "JONES", "MANAGER", Optional.of(7839), LocalDate.parse("1981-04-02"), 2975.0, Optional.empty(), 20));
  add(new Emp2(7654, "MARTIN", "SALESMAN", Optional.of(7698), LocalDate.parse("1981-09-28"), 1250.0, Optional.of(1400.0), 30));
  add(new Emp2(7698, "BLAKE", "MANAGER", Optional.of(7839), LocalDate.parse("1981-05-01"), 2850.0, Optional.empty(), 30));
  add(new Emp2(7782, "CLARK", "MANAGER", Optional.of(7839), LocalDate.parse("1981-06-09"), 2450.0, Optional.empty(), 10));
  add(new Emp2(7788, "SCOTT", "ANALYST", Optional.of(7566), LocalDate.parse("1987-04-19"), 3000.0, Optional.empty(), 20));
  add(new Emp2(7839, "KING", "PRESIDENT", Optional.empty(), LocalDate.parse("1981-11-17"), 5000.0, Optional.empty(), 10));
  add(new Emp2(7844, "TURNER", "SALESMAN", Optional.of(7698), LocalDate.parse("1981-09-08"), 1500.0, Optional.of(0.0), 30));
  add(new Emp2(7876, "ADAMS", "CLERK", Optional.of(7788), LocalDate.parse("1987-05-23"), 1100.0, Optional.empty(), 20));
  add(new Emp2(7900, "JAMES", "CLERK", Optional.of(7698), LocalDate.parse("1981-12-03"), 950.0, Optional.empty(), 30));
  add(new Emp2(7902, "FORD", "ANALYST", Optional.of(7566), LocalDate.parse("1981-12-03"), 3000.0, Optional.empty(), 20));
  add(new Emp2(7934, "MILLER", "CLERK", Optional.of(7782), LocalDate.parse("1982-01-23"), 1300.0, Optional.empty(), 10));
}}
