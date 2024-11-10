package com.dao;
import com.entity.*;

import java.util.*;
public interface AboutDAO {
	About findById(int id);
	void update(About about);
}
