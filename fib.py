# somarinka, len na vyskusanie pridania suboru

def fib(n):
  """fib(n) - vrati n-ty clen Fib. postupnosti
  """
  
  assert((type(n) is int) and (n>0)),"Indexy su nezaporne cele" 
  if n in [0,1]:
    return n
  p,a=0,1
  for k in range(2,n+1):
    a,p=a+p,a
  return a  

# aj tato zmena len skusobna
        