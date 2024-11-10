package com.dao;
import com.entity.*;

import java.util.*;
public interface ApplyDAO {
	List<Apply> selectAll(HashMap map);
	void add(Apply apply);
	Apply findById(int id);
	void update(Apply apply);
	void delete(int id);
}
