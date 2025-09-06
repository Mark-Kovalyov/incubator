package mayton.primes

import java.lang.Math.pow

import mayton.primes.PrimeLib.combinations

object Stohastic {

  /**
    * Вероятность совместного наступления двух независимых событий
    *
    * @param a вероятность события A
    * @param b вероятность события B
    * @return
    */
  def P_intersect(a : Double, b : Double) : Double = a * b

  // Probability of union of two events
  def P_union(a : Double, b : Double) : Double = a + b - a * b

  // Probability of union of three events
  def P_union(a : Double, b : Double, c : Double) : Double = a + b + c - a * b - a * c - b * c + a * b * c

  /**
    * Формула Бернулли. Вероятность появления события (k) раз в (n) независимых исптытаниях при условии
    * что вероятность возникновения события (p)
    *
    * @param k количество раз
    * @param n количество испытаний
    * @param p вероятность возникновения события
    * @return
    */
  def P_bernully(k: BigInt, n: BigInt, p : Double) : Double = {
    combinations(k, n).toDouble * pow(p, k.toDouble) * pow(1.0 - p, (n-k).toDouble)
  }

  /**
    * Формула полной вероятности
    *
    * @param a - вероятность события A
    * @param h - набор несовместных гипотез
    * @return
    */
  def P_full(a : Double, h : Seq[Double]) : Double = {
    h.map(_ * a).sum
  }

  /**
    * Формула Байеса.
    *
    * Пусть событие А может наступить лишь при условнии
    * появления одного из несовместных событий (гипотез) H1,H2...Hn
    * которые образуют полную группу событий. Если событие A уже произошло
    * то вероятности гипотез могут быть переоценены по формулам Байеса
    *
    * @param P_hi вероятность события при условии что гипотеза i выполняется
    * @param i номер гипотезы
    * @param H набор несовместных гипотез
    * @return
    */
  def P_bayes(P_hi : Double, i : Integer, H : Seq[Double]) : Double = {
    P_hi * H(i) / P_full(P_hi,H)
  }

}
