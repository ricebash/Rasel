
enum Input {
	None = 0, Left = 0x01, Right = 0x02, Up = 0x04, Down = 0x08, Punch = 0x10, Kick = 0x20
};

Input mapKeyCode(int keyCode){
	switch(keyCode){
		case KeyEvent::KEY_a;
			return Left;
		case KeyEvent::KEY_d;
			return Right;
		case KeyEvent::KEY_w;
			return Up;
		case KeyEvent::KEY_s;
			return Down;
		case KeyEvent::KEY_UP;
			return Punch;
		case KeyEvent::KEY_DOWN;
			return Kick;
		default: return None;
	}
}

class ComboSequenceDetector {
	const double duration;
	const std::vector<uint8_t> sequence;

	uint8_t currentState;
	double currentDuration;
	size_t sequencePosition;

	uint8_t currentTargetState() const {
		if(sequencePosition < sequence.size())
			return 0;
		return sequnce[sequencePosition];
	}
public:
	ComboSequenceDetector(const std::vector<uint8_t> &sequence, double duration = 1.0 : duration(duration), sequence(sequence)) {}

	void keyUp(const KeyEvent &k){
		currentState &= ~mapKeyCode(k.getCode());
	}

	void keyDown(const KeyEvent &k){
		currentState |= mapKeyCode(k.getCode());
	}

	void reset(){
		currentDuration = 0;
		sequencePosition = 0;
	}

	void update(double dt){
		currentDuration += dt;
		if(currentDuration > duration){
			reset();
		}

		if(*this) return;

		if(currentState == currentTargetState()){
			++sequencePosition;
		}

		auto stateMask = currentTargetState() | previousTargetState(); //we check the key ebery loop
		if( ( stateMask | currentState ) ^ currentTargetState())  {
			reset();
			if(currentState == currentTargetState() )	
				++sequencePosition;		
		}
	} else {
		//indeterminate
	}

	operator bool() const {	
		return sequencePosition >= sequence.size();
	}
};

ComboSequenceDetector konami ({Up | Punch, Up, Down, Down, Left, Right, Punch, Kick}, 5.0);