typedef struct
{
	double x;
	double y;
} POINT;
 
typedef struct
{
	POINT Center;
	int NumSides;
	POINT *Points;
} POLYGON;
 
/* Tests for a collision between any two convex polygons.
 * Returns 0 if they do not intersect
 * Returns 1 if they intersect
 */
int polypolyCollision(POLYGON *a, POLYGON *b)
{
	POINT axis;
	double tmp, minA, maxA, minB, maxB;
	int side, i;
 
	/* test polygon A's sides */
	for (side = 0; side < a->NumSides; side++)
	{
		/* get the axis that we will project onto */
		if (side == 0)
		{
			axis.x = a->Points[a->NumSides - 1].y - a->Points[0].y;
			axis.y = a->Points[0].x - a->Points[a->NumSides - 1].x;
		}
		else
		{
			axis.x = a->Points[side - 1].y - a->Points[side].y;
			axis.y = a->Points[side].x - a->Points[side - 1].x;
		}
 
		/* normalize the axis */
		tmp = sqrt(axis.x * axis.x + axis.y * axis.y);
		axis.x /= tmp;
		axis.y /= tmp;
 
		/* project polygon A onto axis to determine the min/max */
		minA = maxA = a->Points[0].x * axis.x + a->Points[0].y * axis.y;
		for (i = 1; i < a->NumSides; i++)
		{
			tmp = a->Points[i].x * axis.x + a->Points[i].y * axis.y;
			if (tmp > maxA)
				maxA = tmp;
			else if (tmp < minA)
				minA = tmp;
		}
		/* correct for offset */
		tmp = a->Center.x * axis.x + a->Center.y * axis.y;
		minA += tmp;
		maxA += tmp;
 
		/* project polygon B onto axis to determine the min/max */
		minB = maxB = b->Points[0].x * axis.x + b->Points[0].y * axis.y;
		for (i = 1; i < b->NumSides; i++)
		{
			tmp = b->Points[i].x * axis.x + b->Points[i].y * axis.y;
			if (tmp > maxB)
				maxB = tmp;
			else if (tmp < minB)
				minB = tmp;
		}
		/* correct for offset */
		tmp = b->Center.x * axis.x + b->Center.y * axis.y;
		minB += tmp;
		maxB += tmp;
 
		/* test if lines intersect, if not, return false */
		if (maxA < minB || minA > maxB)
			return 0;
	}
 
	/* test polygon B's sides */
	for (side = 0; side < b->NumSides; side++)
	{
		/* get the axis that we will project onto */
		if (side == 0)
		{
			axis.x = b->Points[b->NumSides - 1].y - b->Points[0].y;
			axis.y = b->Points[0].x - b->Points[b->NumSides - 1].x;
		}
		else
		{
			axis.x = b->Points[side - 1].y - b->Points[side].y;
			axis.y = b->Points[side].x - b->Points[side - 1].x;
		}
 
		/* normalize the axis */
		tmp = sqrt(axis.x * axis.x + axis.y * axis.y);
		axis.x /= tmp;
		axis.y /= tmp;
 
		/* project polygon A onto axis to determine the min/max */
		minA = maxA = a->Points[0].x * axis.x + a->Points[0].y * axis.y;
		for (i = 1; i < a->NumSides; i++)
		{
			tmp = a->Points[i].x * axis.x + a->Points[i].y * axis.y;
			if (tmp > maxA)
				maxA = tmp;
			else if (tmp < minA)
				minA = tmp;
		}
		/* correct for offset */
		tmp = a->Center.x * axis.x + a->Center.y * axis.y;
		minA += tmp;
		maxA += tmp;
 
		/* project polygon B onto axis to determine the min/max */
		minB = maxB = b->Points[0].x * axis.x + b->Points[0].y * axis.y;
		for (i = 1; i < b->NumSides; i++)
		{
			tmp = b->Points[i].x * axis.x + b->Points[i].y * axis.y;
			if (tmp > maxB)
				maxB = tmp;
			else if (tmp < minB)
				minB = tmp;
		}
		/* correct for offset */
		tmp = b->Center.x * axis.x + b->Center.y * axis.y;
		minB += tmp;
		maxB += tmp;
 
		/* test if lines intersect, if not, return false */
		if (maxA < minB || minA > maxB)
			return 0;
	}
 
	return 1;
}