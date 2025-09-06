package ru.sql.cdc;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class UtilsTest {

    private static List<Pair<String, String>> toArrayOfPairs(String[] pairs) {
        List<Pair<String, String>> pairList = new ArrayList<>();
        for (int i = 0; i < pairs.length; i += 2) {
            pairList.add(Pair.of(pairs[i], pairs[i + 1]));
        }
        return pairList;
    }

    @Test
    public void testEmptyGroupBy() {
        Iterable<JmsEntity> stream = Utils.groupByTimeOperationTableName(Collections.EMPTY_LIST);
        assertFalse(stream.iterator().hasNext());
    }

    @Test
    public void testSingleTime() {
        List<EavEntity> list = new ArrayList<>() {{
            // INSERT INTO EMP VALUES ( 7369,'SMITH','CLERK',7902,to_date('17-12-1980','dd-mm-yyyy'),800,NULL,20 )
            add(new EavEntity("2020-01-01 16:01:01", "I", "EMP", "EMPNO", "7369"));
            add(new EavEntity("2020-01-01 16:01:01", "I", "EMP", "ENAME", "SMITH"));
            add(new EavEntity("2020-01-01 16:01:01", "I", "EMP", "JOB", "CLERK"));
            add(new EavEntity("2020-01-01 16:01:01", "I", "EMP", "MGR", "7902"));
            add(new EavEntity("2020-01-01 16:01:01", "I", "EMP", "HIREDATE", "17-12-1980"));
            add(new EavEntity("2020-01-01 16:01:01", "I", "EMP", "SAL", "800"));
            add(new EavEntity("2020-01-01 16:01:01", "I", "EMP", "DEPTNO", "20"));
            // INSERT INTO EMP VALUES ( 7499,'ALLEN','SALESMAN',7698,to_date('20-2-1981','dd-mm-yyyy'),1600,300,30 )
            add(new EavEntity("2020-01-01 16:01:02", "I", "EMP", "EMPNO", "7499"));
            add(new EavEntity("2020-01-01 16:01:02", "I", "EMP", "ENAME", "ALLEN"));
            add(new EavEntity("2020-01-01 16:01:02", "I", "EMP", "JOB", "SALESMAN"));
            add(new EavEntity("2020-01-01 16:01:02", "I", "EMP", "MGR", "7698"));
            add(new EavEntity("2020-01-01 16:01:02", "I", "EMP", "HIREDATE", "20-2-1981"));
            add(new EavEntity("2020-01-01 16:01:02", "I", "EMP", "SAL", "1600"));
            add(new EavEntity("2020-01-01 16:01:02", "I", "EMP", "COMM", "300"));
            add(new EavEntity("2020-01-01 16:01:02", "I", "EMP", "DEPTNO", "30"));
            // DELETE FROM EMP WHERE EMPNO = 7369
            add(new EavEntity("2020-01-01 16:01:03", "D", "EMP", "EMPNO", "7369"));
        }};
        Iterator<JmsEntity> iterator = Utils.groupByTimeOperationTableName(list).iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new JmsEntity("2020-01-01 16:01:01", "I", "EMP", toArrayOfPairs(new String[] {
            "EMPNO", "7369",
            "ENAME", "SMITH",
            "JOB", "CLERK",
            "MGR", "7902",
            "HIREDATE", "17-12-1980",
            "SAL", "800",
            "DEPTNO", "20" })), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new JmsEntity("2020-01-01 16:01:02", "I", "EMP", toArrayOfPairs(new String[] {
            "EMPNO", "7499",
            "ENAME", "ALLEN",
            "JOB", "SALESMAN",
            "MGR", "7698",
            "HIREDATE", "20-2-1981",
            "SAL", "1600",
            "COMM", "300",
            "DEPTNO", "30"
        })), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new JmsEntity("2020-01-01 16:01:03", "D", "EMP", toArrayOfPairs(new String[] {"EMPNO", "7369"})), iterator.next());
    }


}
