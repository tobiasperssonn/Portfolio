verify(Input) :-
    see(Input), read(T), read(L), read(S), read(F), seen,
    check(T, L, S, [], F).

% check(T, L, S, U, F)
% T - The transitions in form of adjacency lists
% L - The labeling
% S - Current state
% U - Currently recorded states
% F - CTL Formula to check.

% Literals
% Check if X exist in the current state
check(_, L, S, [], X) :- member([S, Y], L), member(X, Y).   

% Check if X doesn't exist in the current state
check(T, L, S, [], neg(X)) :- 
    \+ check(T, L, S, [], X).

% And
% Check if both F and G exist in the current state 
check(T, L, S, [], and(F,G)) :- 
    check(T, L, S, [], F), check(T, L, S, [], G).

% Or
% Check if F or G exist in the current state 
check(T, L, S, [], or(F,G)) :- 
    check(T, L, S, [], F); check(T, L, S, [], G).

% AX
check(T, L, S, [], ax(X)) :- 
    check(T, L, S, [], neg(ex(neg(X)))).  %De Morgans law

% EX
check(T, L, S, [], ex(X)) :- 
    member([S, O], T),  % Find the list of all the current transitions
    member(K, O),   % Find the next state
    check(T, L, K, [], X). % Check if X exist in the next state


% AG
check(T, L, S, U, ag(X)) :- 
    check(T, L, S, U, neg(ef(neg(X)))).   %De Morgans law

% EG
check(_, _, S, U, eg(_)) :-
	member(S,U).   % We have encountered a loop, i.e EG is valid
check(T, L, S, U, eg(F)) :-
	\+ member(S, U),   % We haven't visited this state before
	check(T, L, S, [], F), % Check if F exist in the current state
	member([S, K], T), % Find the next transistion
	member(NS, K), % Find the next state
        % Continue checking if EG holds, save the current state as visited
	check(T, L, NS, [S|U], eg(F)). 


% EF
check(T, L, S, U, ef(F)) :-
	\+ member(S, U),   % We haven't visited this state before
        % Check if F exist in the current state, i.e EF is valid
	check(T, L, S, [], F). 
check(T, L, S, U, ef(F)) :-
	\+ member(S, U),   % We haven't visited this state before
	member([S, K], T), % Find the next transistion
	member(NS, K), % Find the next state
        % Continue along the path, checking if EF is valid 
        % Save the current state as visited 
	check(T, L, NS, [S|U], ef(F)). 


% AF
check(T, L, S, U, af(X)) :- 
    check(T, L, S, U, neg(eg(neg(X)))).  %De Morgans law