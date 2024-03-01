% From lab 1
appendEl(X, [], [X]).
appendEl(X, [H | T], [H | Y]) :-
  appendEl(X, T, Y).

% Skapa en ny databas med 5 kolumner
:- dynamic db_row/5.

verify(InputFileName) :- see(InputFileName),
    retractall(db_row(_,_,_,_,_)),
    read(Prems), read(Goal), read(Proof),
    seen,
    valid_proof(Prems, Goal, Proof, global).

valid_proof(Prems, Goal, Proof, global) :-
    last(Proof, [_, Goal, _]),
    check_proof(Proof, Prems, global, 0, 0, 0),
    retractall(db_row(_,_,_,_,temporary)).

valid_proof(Prems, Goal, Proof, box, Depth, BoxNum, FirstLine) :-
    last(Proof, [_, Goal, _]),
    check_proof(Proof, Prems, box, Depth, BoxNum, FirstLine),
    retractall(db_row(_,_,_,_,temporary)).


rule(Rule, Body, Prems, Env, Depth, BoxNum, FirstLine) :-
    (Rule = premise -> check_premise(Body, Prems);
    (Rule = assumption, check_ass(Env, FirstLine));
    Rule = copy(X) -> check_copy(X, Body, BoxNum, Depth);
    Rule = andint(Left,Right) -> check_andint(Body, Left, Right);
    Rule = andel1(X) -> check_andel1(X, Body);
    Rule = andel2(X) -> check_andel2(X, Body);
    Rule = orint1(X) -> check_orint1(X);
    Rule = orint2(X) -> check_orint2(X);
    Rule = orel(X,Y,U,V,W) -> check_orel(X,Y,U,V,W,Body);
    Rule = impint(X,Y) -> check_impint(X,Y,Body,Depth);
    Rule = impel(X,Imp) -> check_impel(X, Imp, Body);
    Rule = negint(X,Y) -> check_negint(X, Y, Body);
    Rule = negel(X,Y) -> check_negel(X, Y, Body);
    Rule = contel(X) -> check_contel(X);
    Rule = negnegint(X) -> check_negnegint(X, Body);
    Rule = negnegel(X) -> check_negnegel(X);
    Rule = mt(X,Y) -> check_mt(X, Y);
    Rule = pbc(X,Y) -> check_pbc(X, Y, Body);
    Rule = lem -> check_lem(Body)).


open_box(Prems, Proof, Goal, Depth, BoxNum) :-
    NewDepth is Depth + 1,
    Proof=[[_,_,assumption]|_],
    valid_proof(Prems, Goal, Proof, box, NewDepth, BoxNum, 1).

check_premise(Body, Prems) :- member(Body, Prems).

check_ass(Env, FirstLine) :- Env=box, FirstLine = 1.

check_copy(X, Body, BoxNum, Depth) :- db_row(X, Body, OtherBoxNum, OtherDepth, _), (BoxNum=OtherBoxNum,OtherDepth=<Depth;OtherBoxNum=0).

check_andint(Body, Left, Right) :- db_row(Left, X, _, _,_), db_row(Right, Y, _, _,_), Body=and(X,Y). 
check_andel1(X, Body) :- db_row(X, and(Body, _),_, _, _).
check_andel2(X, Body) :- db_row(X, and(_, Body),_, _, _).

check_orint1(X) :- db_row(X, _, _, _,_). 
check_orint2(X) :- db_row(X, _, _, _,_). 
check_orel(X,Y,U,V,W,Body) :- db_row(X, or(Left, Right), _, _,_), db_row(Y, Left, BoxNum1,_, _), db_row(U, Body, BoxNum1, _,_), db_row(V, Right, BoxNum2, _,_), db_row(W, Body, BoxNum2, _,_).

check_impint(X,Y,Body,Depth) :- db_row(X, Left, BoxNum, OtherDepth, _), db_row(Y, Right, BoxNum, OtherDepth, _), Body=imp(Left, Right), (OtherDepth-1)=:=Depth.
check_impel(X, Imp, Body) :- db_row(X, Y, _, _,_), db_row(Imp, imp(Y,Z), _, _,_), Body=Z.

check_negint(X,Y,Body) :- db_row(X, Z, BoxNum, _,_), db_row(Y, cont, BoxNum, _,_), Body=neg(Z).
check_negel(X, Y, Body) :- Body=cont, db_row(X, Z, _, _,_), db_row(Y, W, _, _,_), (Z = neg(W) ; W = neg(Z)). 

check_contel(X) :- db_row(X, Y, _, _,_), Y=cont.

check_negnegint(X, Body) :- db_row(X, Y, _, _,_), Body = neg(neg(Y)). 
check_negnegel(X) :- db_row(X, Y, _, _,_), Y=neg(neg(_)).

check_mt(X, Y) :- db_row(X,Z,_, _,_), db_row(Y, W, _, _,_), Z = imp(_,R), W = neg(R).

check_pbc(X, Y, Body) :- db_row(X, neg(Body), BoxNum, _,_), db_row(Y, cont, BoxNum, _,_). 

check_lem(Body) :- Body = or(X, neg(X)).

% Loopa tills bevis listan är tom
check_proof([], _, _, _, _, _).

% Denna check_proof känner igen när vi kommer till en box
check_proof([L|T], Prems, Env, Depth, BoxNum, FirstLine) :-
    L=[[_,_,_]|_],
    last(L, [_,Goal,_]),
    (Env=global, NewBoxNum is BoxNum + 1; Env=box, NewBoxNum is BoxNum),
    open_box(Prems, L, Goal, Depth, NewBoxNum),
    check_proof(T, Prems, Env, Depth, NewBoxNum, FirstLine).

% Denna körs check_proof körs för varje bevisrad
check_proof([[Num, Body, Rule] | Rest], Prems, Env, Depth, BoxNum, FirstLine) :-
    rule(Rule, Body, Prems, Env, Depth, BoxNum, FirstLine),
    (Env=global, Temp=notTemporary; Env=box, \+(Rule=assumption;Rest=[]), Temp=temporary; Env=box, (Rule=assumption;Rest=[]), Temp=notTemporary),
    assertz(db_row(Num, Body, BoxNum, Depth, Temp)),
    check_proof(Rest, Prems, Env, Depth, BoxNum, 0).