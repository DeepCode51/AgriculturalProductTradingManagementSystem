package com.dao;
import com.entity.*;

import java.util.*;
public interface JbrecordDAO {
	List<Jbrecord> selectAll(HashMap map);
	void add(Jbrecord jbrecord);
	Jbrecord findById(int id);
	void delete(int id);
}
