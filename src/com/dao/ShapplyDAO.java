package com.dao;
import com.entity.*;

import java.util.*;
public interface ShapplyDAO {
	List<Shapply> selectAll(HashMap map);
	void add(Shapply shapply);
	Shapply findById(int id);
	void update(Shapply shapply);
	void delete(int id);
}
