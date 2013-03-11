#include <iostream>
#include <map>
#include <vector>
 
class LinearInterpolator {
	public:
		LinearInterpolator() {}
 
		void addDataPoint(double x, std::vector<double> &d) {
			// just add it to the map
			data[x] = d;
		}
 
		double interpolate(double x, unsigned int column) {
			// loop through all the keys in the map
			// to find one that is greater than our intended value
			std::map<double, std::vector<double> >::iterator it = data.begin();
			bool found = false;
			while(it != data.end() && !found) {
				if(it->first >= x) {
					found = true;
					break;
				}
 
				// advance the iterator
				it++;
			}
 
			// check to see if we're outside the data range
			if(it == data.begin()) {
				return data.begin()->second[column];
			}
			else if(it == data.end()) {
				// move the point back one, as end() points past the list
				it--;
				return it->second[column];
			}
			// check to see if we landed on a given point
			else if(it->first == x) {
				return it->second[column];
			}
 
			// nope, we're in the range somewhere
			// collect some values
			double xb = it->first;
			double yb = it->second[column];
			it--;
			double xa = it->first;
			double ya = it->second[column];
 
			// and calculate the result!
			// formula from Wikipedia
			return (ya + (yb - ya) * (x - xa) / (xb - xa));
		}
 
	private:
		std::map<double, std::vector<double> > data;
};
 
int main() {
	LinearInterpolator interp;
 
	// create some "normal" tissue
	std::vector<double> normal;
	normal.push_back(990);
	normal.push_back(2.25e9);
 
	// create some "abnormal" tissue
	std::vector<double> abnormal;
	abnormal.push_back(940);
	abnormal.push_back(2.35e9);
 
	// add data points to the interpolator
	interp.addDataPoint(0, normal);
	interp.addDataPoint(0.044, normal);
	interp.addDataPoint(0.045, abnormal);
	interp.addDataPoint(0.055, abnormal);
	interp.addDataPoint(0.056, normal);
	interp.addDataPoint(0.1, normal);
 
	// now loop through a bunch of points and interpolate at each of them
	std::cout << "x\trho\tG" << std::endl;
	for(double d = -0.01; d <= 0.11; d += 0.01) {
		std::cout << d << "\t" << interp.interpolate(d, 0) << "\t" << interp.interpolate(d, 1) << std::endl;
	}
}