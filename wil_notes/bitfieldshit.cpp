struct A {
	int flag : 1;
};

struct DynamicBitField{
	uint8_ *bits;
	size_t size;

	bool ValueAt(size_t pos){
		return ((bit[pos/8] >> (pos%8)) & 1; //mask with 1
		// similarly, (bit[pos/8] & (1 << (pos % 8)))
	}
};

use stupid boost/dynamic_bitset

some notes on jumping::
the initial velocity is not integrated
only the next bluh