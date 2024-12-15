import { createContext, ReactNode, useState } from 'react'
import { User } from '../../Data/User';

interface UserContextType {
  user: User | null;
  login: (id: number, username: string, role: number) => void;
  logout: () => void;
}

export const UserContext = createContext<UserContextType | undefined>(undefined);

interface UserProviderProps {
  children: ReactNode
}

export const UserProvider: React.FC<UserProviderProps> = ({children}) => {
  const [user, setUser] = useState<User | null>(null);

  function login(id: number, username: string, role: number)
  {
    setUser({id, username, role});
  }

  function logout()
  {
    setUser(null);
  }

  return (
    <UserContext.Provider value={{user, login, logout}}>
      {children}
    </UserContext.Provider>
  )
}
