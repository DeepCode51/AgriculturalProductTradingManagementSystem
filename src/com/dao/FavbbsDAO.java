package com.dao;
import com.entity.*;

import java.util.*;
public interface FavbbsDAO {
	List<Favbbs> selectAll(HashMap map);
	void add(Favbbs favbbs);
	Favbbs findById(int id);
	void delete(int id);
}
