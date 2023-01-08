export class Menu {

  title: string;
  route: string;
  icon?: string;
  children: Menu[];
  roles?: string[];

  constructor(data?: any) {
    if (data) {
      Object.assign(this, data);
      if (this.children) {
        this.children.forEach((child: any) => {
          child = new Menu(child);
        });
      }
    }
  }
}
