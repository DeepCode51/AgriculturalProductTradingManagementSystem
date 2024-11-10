package com.dao;
import com.entity.*;

import java.util.*;
public interface InventoryDAO {
	List<Inventory> selectAll(HashMap map);
	void add(Inventory inventory);
}
