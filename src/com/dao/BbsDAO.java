package com.dao;
import com.entity.*;

import java.util.*;
public interface BbsDAO {
	List<Bbs> selectAll(HashMap map);
	void add(Bbs bbs);
	Bbs findById(int id);
	void update(Bbs bbs);
	void delete(int id);
}
